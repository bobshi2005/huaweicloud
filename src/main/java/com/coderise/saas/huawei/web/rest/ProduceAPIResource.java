package com.coderise.saas.huawei.web.rest;

import com.coderise.saas.huawei.config.ApplicationProperties;
import com.coderise.saas.huawei.domain.ProduceExtend;
import com.coderise.saas.huawei.domain.ProduceNew;
import com.coderise.saas.huawei.repository.InstanceRepository;
import com.coderise.saas.huawei.repository.ProduceExpireRepository;
import com.coderise.saas.huawei.repository.ProduceExtendRepository;
import com.coderise.saas.huawei.repository.ProduceNewRepository;
import com.coderise.saas.huawei.repository.ProduceReleaseRepository;
import com.coderise.saas.huawei.service.InstanceService;
import com.coderise.saas.huawei.service.ProduceExpireService;
import com.coderise.saas.huawei.service.ProduceExtendService;
import com.coderise.saas.huawei.service.ProduceNewService;
import com.coderise.saas.huawei.service.ProduceReleaseService;
import com.coderise.saas.huawei.service.dto.InstanceDTO;
import com.coderise.saas.huawei.service.dto.ProduceExpireDTO;
import com.coderise.saas.huawei.service.dto.ProduceExtendDTO;
import com.coderise.saas.huawei.service.dto.ProduceNewDTO;
import com.coderise.saas.huawei.service.dto.ProduceReleaseDTO;
import com.coderise.saas.huawei.service.mapper.InstanceMapper;
import com.coderise.saas.huawei.service.mapper.ProduceExpireMapper;
import com.coderise.saas.huawei.service.mapper.ProduceExtendMapper;
import com.coderise.saas.huawei.service.mapper.ProduceNewMapper;
import com.coderise.saas.huawei.service.mapper.ProduceReleaseMapper;
import com.coderise.saas.huawei.service.util.HuaWeiSaasResult;
import com.coderise.saas.huawei.service.util.HuaWeiSaasUtil;
import com.coderise.saas.huawei.service.util.RandomUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/** ProduceAPI controller */
@RestController
@RequestMapping("/api")
public class ProduceAPIResource {

  public static final DateTimeFormatter FORMATTER =
      new DateTimeFormatterBuilder()
          .appendPattern("yyyyMMddHHmmss")
          .appendValue(ChronoField.MILLI_OF_SECOND, 3)
          .toFormatter()
          .withZone(ZoneId.systemDefault());
  // DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").withZone(ZoneId.systemDefault());
  public static final DateTimeFormatter FORMATTER_yyyyMMddHHmmss =
      DateTimeFormatter.ofPattern("yyyyMMddHHmmss").withZone(ZoneId.systemDefault());
  private final Logger log = LoggerFactory.getLogger(ProduceAPIResource.class);
  private final ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
  private final ApplicationProperties applicationProperties;
  private final RestTemplateBuilder builder;
  private final ProduceNewService produceNewService;
  private final ProduceExtendService produceExtendService;
  private final ProduceReleaseService produceReleaseService;
  private final ProduceExpireService produceExpireService;
  private final InstanceService instanceSerivce;
  private final ProduceNewRepository produceNewRepository;
  private final ProduceExtendRepository produceExtendRepository;
  private final ProduceReleaseRepository produceReleaseRepository;
  private final ProduceExpireRepository produceExpireRepository;
  private final InstanceRepository instanceRepository;
  private final ProduceNewMapper produceNewMapper;
  private final ProduceExtendMapper produceExtendMapper;
  private final ProduceExpireMapper produceExpireMapper;
  private final ProduceReleaseMapper produceReleaseMapper;
  private final InstanceMapper instanceMapper;

  public ProduceAPIResource(
      ApplicationProperties applicationProperties,
      RestTemplateBuilder builder,
      ProduceNewService produceNewService,
      ProduceExtendService produceExtendService,
      ProduceReleaseService produceReleaseService,
      ProduceExpireService produceExpireService,
      InstanceService instanceSerivce,
      ProduceNewRepository produceNewRepository,
      ProduceExtendRepository produceExtendRepository,
      ProduceReleaseRepository produceReleaseRepository,
      ProduceExpireRepository produceExpireRepository,
      InstanceRepository instanceRepository,
      ProduceNewMapper produceNewMapper,
      ProduceExtendMapper produceExtendMapper,
      ProduceExpireMapper produceExpireMapper,
      ProduceReleaseMapper produceReleaseMapper,
      InstanceMapper instanceMapper) {
    this.applicationProperties = applicationProperties;
    this.builder = builder;
    this.produceNewService = produceNewService;
    this.produceExtendService = produceExtendService;
    this.produceReleaseService = produceReleaseService;
    this.produceExpireService = produceExpireService;
    this.instanceSerivce = instanceSerivce;
    this.produceNewRepository = produceNewRepository;
    this.produceExtendRepository = produceExtendRepository;
    this.produceReleaseRepository = produceReleaseRepository;
    this.produceExpireRepository = produceExpireRepository;
    this.instanceRepository = instanceRepository;
    this.produceNewMapper = produceNewMapper;
    this.produceExtendMapper = produceExtendMapper;
    this.produceExpireMapper = produceExpireMapper;
    this.produceReleaseMapper = produceReleaseMapper;
    this.instanceMapper = instanceMapper;
  }

  /** GET defaultAction */
  @GetMapping("/saasproduce")
  public ResponseEntity<String> defaultAction(
      @RequestParam String activity, @RequestParam boolean testFlag, HttpServletRequest request) {
    // verify the auth code
    boolean isVerifiedOk =
        HuaWeiSaasUtil.verificateRequestParams(
            request, applicationProperties.getSaasIntegration().getKey(), 256);
    if (!isVerifiedOk) {
      return this.getSignedResponseEntity(this.getJsonStr(createAuthFailedMsg()));
    }
    // do the real jobs
    switch (activity) {
      case Activities.newInstance:
        ProduceNewDTO requestDTO = getProduceNewDTO(request);
        if (!isValidNewRequest(requestDTO)) {
          return getSignedResponseEntity(getJsonStr(createParamterIllegal()));
        }
        // assume test request will have special orderId which will not impact the real order ids
        Optional<ProduceNew> savedDTO =
            produceNewRepository.findOneByOrderId(requestDTO.getOrderId());
        if (savedDTO.isPresent()) {
          // old orderId
          InstanceDTO savedInstanceDTO = instanceMapper.toDto(savedDTO.get().getInstance());
          Map<String, Object> responseResult = getProduceNewResponse(savedInstanceDTO);
          return getSignedResponseEntity(getJsonStr(responseResult));
        }
        // new orderId
        InstanceDTO instanceDTO = new InstanceDTO();
        instanceDTO.setAppAdminUrl(applicationProperties.getUserApi().getAdminUrl());
        instanceDTO.setAppFrontEndUrl(applicationProperties.getUserApi().getFrontUrl());
        instanceDTO.setExpireTime(requestDTO.getExpireTime());
        instanceDTO.setIsReleased(false);
        instanceDTO.setPassword(RandomUtil.generatePassword());
        instanceDTO.setUserName(requestDTO.getMobilePhone());
        instanceDTO.setIsTrial(requestDTO.isTrialFlag());
        instanceDTO.setUpdateTime(Instant.now());

        //         no real action do when test request received
        if (requestDTO.isTestFlag()) {
          instanceDTO.setResultCode("1");
          instanceDTO.setResultMsg("TEST RECORD IS CREATED");
        } else {
          Map<String, Object> instanceCreatedResult =
              createUserInstance(builder.build(), requestDTO, instanceDTO);
          if (instanceCreatedResult != null) {
            //          log.info(instanceCreatedResult.toString());
            instanceDTO.setResultCode(instanceCreatedResult.get("code").toString());
            instanceDTO.setResultMsg(
                instanceCreatedResult.get("data") == null
                    ? "成功创建"
                    : instanceCreatedResult.get("data").toString());
          }
        }
        // save to db...
        instanceDTO = instanceSerivce.save(instanceDTO);

        requestDTO.setInstanceId(instanceDTO.getId());
        requestDTO = produceNewService.save(requestDTO);

        Map<String, Object> responseResult = getProduceNewResponse(instanceDTO);
        return getSignedResponseEntity(getJsonStr(responseResult));
      case Activities.refreshInstance:
        ProduceExtendDTO extendDTO = getProduceExtendDTO(request);
        if (!isValidExtendRequest(extendDTO)) {
          return getSignedResponseEntity(getJsonStr(createParamterIllegal()));
        }
        Optional<ProduceExtend> savedExtendDTO =
            produceExtendRepository.findOneByOrderId(extendDTO.getOrderId());
        if (savedExtendDTO.isPresent()) {
          // old order id
          return getSignedResponseEntity(
              getJsonStr(createSimpleResultMsg(HuaWeiSaasResult.COMMON_SUCCESS)));
        }
        // no need to call the real server

        extendDTO = produceExtendService.save(extendDTO);
        InstanceDTO instanceDTO1 = instanceSerivce.findOne(extendDTO.getInstanceId());
        if (instanceDTO1 == null) {
          log.info("instance id : " + instanceDTO1.getId() + " not found");
          return getSignedResponseEntity(getJsonStr(createParamterIllegal()));
        }
        if (!extendDTO.isTestFlag()) {
          manageUserInstance(builder.build(), instanceDTO1, false);
        }
        return getSignedResponseEntity(
            getJsonStr(createSimpleResultMsg(HuaWeiSaasResult.COMMON_SUCCESS)));
      case Activities.expireInstance:
        ProduceExpireDTO expireDTO = getProduceExpireDTO(request);
        if (!isValidExpireRequest(expireDTO)) {
          return getSignedResponseEntity(getJsonStr(createParamterIllegal()));
        }
        produceExpireService.save(expireDTO);

        InstanceDTO instanceDTO2 = instanceSerivce.findOne(expireDTO.getInstanceId());
        if (instanceDTO2 == null) {
          log.info("instance id : " + instanceDTO2.getId() + " not found");
          return getSignedResponseEntity(getJsonStr(createParamterIllegal()));
        }
        if (!expireDTO.isTestFlag()) {
          manageUserInstance(builder.build(), instanceDTO2, true);
        }
        return getSignedResponseEntity(
            getJsonStr(createSimpleResultMsg(HuaWeiSaasResult.COMMON_SUCCESS)));

      case Activities.releaseInstance:
        ProduceReleaseDTO releaseDTO = getProduceReleaseDTO(request);
        if (!isValidReleaseRequest(releaseDTO)) {
          return getSignedResponseEntity(getJsonStr(createParamterIllegal()));
        }
        produceReleaseService.save(releaseDTO);

        InstanceDTO instanceDTO3 = instanceSerivce.findOne(releaseDTO.getInstanceId());
        if (instanceDTO3 == null) {
          log.info("instance id : " + instanceDTO3.getId() + " not found");
          return getSignedResponseEntity(getJsonStr(createParamterIllegal()));
        }
        if (!releaseDTO.isTestFlag()) {
          manageUserInstance(builder.build(), instanceDTO3, true);
        }
        return getSignedResponseEntity(
            getJsonStr(createSimpleResultMsg(HuaWeiSaasResult.COMMON_SUCCESS)));
      default:
        break;
    }
    return this.getSignedResponseEntity(
        getJsonStr(createSimpleResultMsg(HuaWeiSaasResult.COMMON_PARAMETER_ILLEGAL)));
  }

  private Map<String, Object> getProduceNewResponse(InstanceDTO instanceDTO) {
    Map<String, Object> responseResult;
    if (instanceDTO.getResultCode().equals("1")) {
      responseResult = createSimpleResultMsg(HuaWeiSaasResult.COMMON_SUCCESS);
      responseResult.put(
          "instanceId",
          HuaWeiSaasUtil.generateSaaSUsernameOrPwd(
              applicationProperties.getSaasIntegration().getKey(), instanceDTO.getId() + "", 256));
      Map<String, String> appInfo = new HashMap<>();
      appInfo.put("frontEndUrl", instanceDTO.getAppFrontEndUrl());
      appInfo.put("adminUrl", instanceDTO.getAppAdminUrl());
      appInfo.put(
          "userName",
          HuaWeiSaasUtil.generateSaaSUsernameOrPwd(
              applicationProperties.getSaasIntegration().getKey(), instanceDTO.getUserName(), 256));
      appInfo.put(
          "password",
          HuaWeiSaasUtil.generateSaaSUsernameOrPwd(
              applicationProperties.getSaasIntegration().getKey(), instanceDTO.getPassword(), 256));
      responseResult.put("appInfo", appInfo);
    } else {
      responseResult = createSimpleResultMsg(HuaWeiSaasResult.COMMON_INTERNAL_SERVER_ERROR);
      responseResult.put(
          "instanceId",
          HuaWeiSaasUtil.generateSaaSUsernameOrPwd(
              applicationProperties.getSaasIntegration().getKey(), instanceDTO.getId() + "", 256));
    }
    return responseResult;
  }

  private ResponseEntity<String> getSignedResponseEntity(String body) {
    String signValue = "";
    try {
      signValue =
          HuaWeiSaasUtil.generateResponseBodySignature(
              this.applicationProperties.getSaasIntegration().getKey(), body);
    } catch (Exception e) {
      log.error("sign response error.", e);
    }
    String signHeaderValue = "sign_type=\"HMAC-SHA256\", signature= \"" + signValue + "\"";
    return ResponseEntity.ok().header("Body-Sign", signHeaderValue).body(body);
  }

  private String getJsonStr(Map<String, Object> map) {
    String resultStr = "";
    try {
      resultStr = objectMapper.writeValueAsString(map);
    } catch (JsonProcessingException e) {
      log.error("convert to json error", e);
    }

    return resultStr;
  }

  private Map<String, Object> createSimpleResultMsg(HuaWeiSaasResult result) {
    Map<String, Object> msg = new HashMap<>();
    msg.put("resultCode", result.getCode());
    msg.put("resultMsg", result.getMessage());
    return msg;
  }

  private Map<String, Object> createAuthFailedMsg() {
    return createSimpleResultMsg(HuaWeiSaasResult.COMMON_AUTH_FAILED);
  }

  private Map<String, Object> createTestMsg() {
    return createSimpleResultMsg(HuaWeiSaasResult.COMMON_SUCCESS);
  }

  private Map<String, Object> createParamterIllegal() {
    return createSimpleResultMsg(HuaWeiSaasResult.COMMON_PARAMETER_ILLEGAL);
  }

  private ProduceReleaseDTO getProduceReleaseDTO(HttpServletRequest request) {
    Map<String, String[]> paramsMap = request.getParameterMap();
    ProduceReleaseDTO newDTO = new ProduceReleaseDTO();
    String[] parameter;
    parameter = paramsMap.get("timeStamp");
    if (null != parameter && parameter.length == 1) {
      try {
        newDTO.setTimeStamp(Instant.from(FORMATTER.parse(parameter[0])));
      } catch (Exception e) {
        log.error("get timestamp error", e);
      }
    }
    parameter = paramsMap.get("testFlag");
    if (null != parameter && parameter.length == 1) {
      newDTO.setTestFlag(parameter[0].equals("1"));
    }
    parameter = paramsMap.get("instanceId");
    if (null != parameter && parameter.length == 1) {
      try {
        newDTO.setInstanceId(
            Long.parseLong(
                HuaWeiSaasUtil.decryptMobilePhoneOrEMail(
                    applicationProperties.getSaasIntegration().getKey(), parameter[0], 256)));
      } catch (NumberFormatException e) {
        log.error("Get Instance Id Error", e);
      }
    }
    return newDTO;
  }

  private ProduceExpireDTO getProduceExpireDTO(HttpServletRequest request) {
    Map<String, String[]> paramsMap = request.getParameterMap();
    ProduceExpireDTO newDTO = new ProduceExpireDTO();
    String[] parameter;
    parameter = paramsMap.get("timeStamp");
    if (null != parameter && parameter.length == 1) {
      try {
        newDTO.setTimeStamp(Instant.from(FORMATTER.parse(parameter[0])));
      } catch (Exception e) {
        log.error("get timestamp error", e);
      }
    }
    parameter = paramsMap.get("testFlag");
    if (null != parameter && parameter.length == 1) {
      newDTO.setTestFlag(parameter[0].equals("1"));
    }
    parameter = paramsMap.get("instanceId");
    if (null != parameter && parameter.length == 1) {
      try {
        newDTO.setInstanceId(
            Long.parseLong(
                HuaWeiSaasUtil.decryptMobilePhoneOrEMail(
                    applicationProperties.getSaasIntegration().getKey(), parameter[0], 256)));
      } catch (NumberFormatException e) {
        log.error("Get Instance Id Error", e);
      }
    }
    return newDTO;
  }

  private ProduceExtendDTO getProduceExtendDTO(HttpServletRequest request) {
    Map<String, String[]> paramsMap = request.getParameterMap();
    ProduceExtendDTO newDTO = new ProduceExtendDTO();
    String[] parameter;
    parameter = paramsMap.get("timeStamp");
    if (null != parameter && parameter.length == 1) {
      try {
        newDTO.setTimeStamp(Instant.from(FORMATTER.parse(parameter[0])));
      } catch (Exception e) {
        log.error("get timestamp error", e);
      }
    }
    parameter = paramsMap.get("orderId");
    if (null != parameter && parameter.length == 1) {
      newDTO.setOrderId(parameter[0]);
    }
    parameter = paramsMap.get("productId");
    if (null != parameter && parameter.length == 1) {
      newDTO.setProductId(parameter[0]);
    }
    parameter = paramsMap.get("testFlag");
    if (null != parameter && parameter.length == 1) {
      newDTO.setTestFlag(parameter[0].equals("1"));
    }
    parameter = paramsMap.get("expireTime");
    if (null != parameter && parameter.length == 1) {
      try {
        newDTO.setExpireTime(ZonedDateTime.from(FORMATTER_yyyyMMddHHmmss.parse(parameter[0])));
      } catch (Exception e) {
        log.error("get expiredTime error", e);
      }
    }
    parameter = paramsMap.get("trialToFormal");
    if (null != parameter && parameter.length == 1) {
      newDTO.setTrialToFormal(parameter[0].equals("1"));
    }
    parameter = paramsMap.get("instanceId");
    if (null != parameter && parameter.length == 1) {
      try {
        newDTO.setInstanceId(
            Long.parseLong(
                HuaWeiSaasUtil.decryptMobilePhoneOrEMail(
                    applicationProperties.getSaasIntegration().getKey(), parameter[0], 256)));
      } catch (NumberFormatException e) {
        log.error("Get Instance Id Error", e);
      }
    }
    return newDTO;
  }

  private ProduceNewDTO getProduceNewDTO(HttpServletRequest request) {
    Map<String, String[]> paramsMap = request.getParameterMap();
    ProduceNewDTO newDTO = new ProduceNewDTO();
    String[] parameter;
    parameter = paramsMap.get("timeStamp");
    if (null != parameter && parameter.length == 1) {
      try {
        newDTO.setTimeStamp(Instant.from(FORMATTER.parse(parameter[0])));
      } catch (Exception e) {
        log.error("get timestamp error", e);
      }
    }
    parameter = paramsMap.get("authToken");
    if (null != parameter && parameter.length == 1) {
      newDTO.setAuthToken(parameter[0]);
    }
    parameter = paramsMap.get("customerId");
    if (null != parameter && parameter.length == 1) {
      newDTO.setCustomerId(parameter[0]);
    }
    parameter = paramsMap.get("customerName");
    if (null != parameter && parameter.length == 1) {
      newDTO.setCustomerName(parameter[0]);
    }
    parameter = paramsMap.get("mobilePhone");
    if (null != parameter && parameter.length == 1) {
      newDTO.setMobilePhone(
          HuaWeiSaasUtil.decryptMobilePhoneOrEMail(
              this.applicationProperties.getSaasIntegration().getKey(), parameter[0], 256));
    }
    parameter = paramsMap.get("email");
    if (null != parameter && parameter.length == 1) {
      newDTO.setEmail(
          HuaWeiSaasUtil.decryptMobilePhoneOrEMail(
              this.applicationProperties.getSaasIntegration().getKey(), parameter[0], 256));
    }
    parameter = paramsMap.get("businessId");
    if (null != parameter && parameter.length == 1) {
      newDTO.setBusinessId(parameter[0]);
    }
    parameter = paramsMap.get("orderId");
    if (null != parameter && parameter.length == 1) {
      newDTO.setOrderId(parameter[0]);
    }
    parameter = paramsMap.get("skuCode");
    if (null != parameter && parameter.length == 1) {
      newDTO.setSkuCode(parameter[0]);
    }
    parameter = paramsMap.get("productId");
    if (null != parameter && parameter.length == 1) {
      newDTO.setProductId(parameter[0]);
    }
    parameter = paramsMap.get("trialFlag");
    if (null != parameter && parameter.length == 1) {
      newDTO.setTrialFlag(parameter[0].equals("1"));
    }
    parameter = paramsMap.get("testFlag");
    if (null != parameter && parameter.length == 1) {
      newDTO.setTestFlag(parameter[0].equals("1"));
    }
    parameter = paramsMap.get("expireTime");
    if (null != parameter && parameter.length == 1) {
      try {
        newDTO.setExpireTime(ZonedDateTime.from(FORMATTER_yyyyMMddHHmmss.parse(parameter[0])));
      } catch (Exception e) {
        log.error("get expire Time error", e);
      }
    }
    return newDTO;
  }

  public boolean isValidNewRequest(ProduceNewDTO newDTO) {
    return !(newDTO.getAuthToken() == null
        || newDTO.getTimeStamp() == null
        || newDTO.getCustomerId() == null
        || newDTO.getBusinessId() == null
        || newDTO.getMobilePhone() == null
        || newDTO.getOrderId() == null
        || newDTO.getProductId() == null);
  }

  public boolean isValidExtendRequest(ProduceExtendDTO extendDTO) {
    return !(extendDTO.getOrderId() == null
        || extendDTO.getInstanceId() == null
        || extendDTO.getExpireTime() == null
        || extendDTO.getTimeStamp() == null);
  }

  public boolean isValidExpireRequest(ProduceExpireDTO expireDTO) {
    return !(expireDTO.getInstanceId() == null || expireDTO.getTimeStamp() == null);
  }

  public boolean isValidReleaseRequest(ProduceReleaseDTO expireDTO) {
    return !(expireDTO.getInstanceId() == null || expireDTO.getTimeStamp() == null);
  }

  private Map<String, Object> manageUserInstance(
      RestTemplate restTemplate, InstanceDTO instanceDTO, boolean isLocked) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

    String timeStamp = FORMATTER.format(Instant.now());
    Map<String, String[]> paramsMap = new HashMap<>();
    paramsMap.put("account", new String[] {instanceDTO.getUserName()});
    paramsMap.put("isLocked", new String[] {isLocked ? "1" : "0"});
    paramsMap.put("timeStamp", new String[] {timeStamp});

    UriComponentsBuilder builder =
        UriComponentsBuilder.fromHttpUrl(this.applicationProperties.getUserApi().getManageUserUrl())
            .queryParam("account", instanceDTO.getUserName())
            .queryParam("timeStamp", timeStamp)
            .queryParam("isLocked", isLocked ? "1" : "0")
            .queryParam(
                "authToken",
                HuaWeiSaasUtil.getAuthToken(
                    applicationProperties.getSaasIntegration().getKey(), paramsMap, timeStamp));

    HttpEntity<?> entity = new HttpEntity<>(headers);

    HttpEntity<String> response =
        restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
    TypeReference<HashMap<String, Object>> typeRef =
        new TypeReference<HashMap<String, Object>>() {};
    try {
      return objectMapper.readValue(response.getBody(), typeRef);
    } catch (IOException e) {
      log.error("manage user error", e);
      return null;
    }
  }

  private Map<String, Object> createUserInstance(
      RestTemplate restTemplate, ProduceNewDTO produceNewDTO, InstanceDTO instanceDTO) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

    String timeStamp = FORMATTER.format(Instant.now());
    Map<String, String[]> paramsMap = new HashMap<>();
    paramsMap.put("account", new String[] {produceNewDTO.getMobilePhone()});
    paramsMap.put("password", new String[] {instanceDTO.getPassword()});
    paramsMap.put(
        "name",
        new String[] {
          (produceNewDTO.getCustomerName() == null && !produceNewDTO.getCustomerName().isEmpty())
              ? produceNewDTO.getMobilePhone()
              : produceNewDTO.getCustomerName()
        });
    paramsMap.put("company", new String[] {produceNewDTO.getMobilePhone()});
    paramsMap.put("email", new String[] {produceNewDTO.getEmail()});
    paramsMap.put("phone", new String[] {produceNewDTO.getMobilePhone()});
    paramsMap.put("timeStamp", new String[] {timeStamp});

    UriComponentsBuilder builder =
        UriComponentsBuilder.fromHttpUrl(this.applicationProperties.getUserApi().getCreateUserUrl())
            .queryParam("account", produceNewDTO.getMobilePhone())
            .queryParam("password", instanceDTO.getPassword())
            .queryParam(
                "name",
                (produceNewDTO.getCustomerName() == null
                        && !produceNewDTO.getCustomerName().isEmpty())
                    ? produceNewDTO.getMobilePhone()
                    : produceNewDTO.getCustomerName())
            .queryParam("company", produceNewDTO.getMobilePhone())
            .queryParam("email", produceNewDTO.getEmail())
            .queryParam("phone", produceNewDTO.getMobilePhone())
            .queryParam("timeStamp", timeStamp)
            .queryParam(
                "authToken",
                HuaWeiSaasUtil.getAuthToken(
                    applicationProperties.getSaasIntegration().getKey(), paramsMap, timeStamp));

    HttpEntity<?> entity = new HttpEntity<>(headers);

    Map<String, Object> mockResult = new HashMap<>();
    HttpEntity<String> response = null;
    try {
      response =
          restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
    } catch (RestClientException e) {
      log.error("call real Create User API error", e);
      mockResult.put("code", "0");
      mockResult.put("data", e.getMessage());
      return mockResult;
    }
    TypeReference<HashMap<String, Object>> typeRef =
        new TypeReference<HashMap<String, Object>>() {};
    try {
      return objectMapper.readValue(response.getBody(), typeRef);
    } catch (IOException e) {
      log.error("convert Create User Response error", e);
      mockResult.put("code", "0");
      mockResult.put("data", e.getMessage());
      return mockResult;
    }
  }

  public interface Activities {
    String expireInstance = "expireInstance";
    String newInstance = "newInstance";
    String refreshInstance = "refreshInstance";
    String releaseInstance = "releaseInstance";
  }
}
