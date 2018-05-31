package com.coderise.saas.huawei.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Huawei Saas.
 *
 * <p>Properties are configured in the application.yml file. See {@link
 * io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

  private final SaasIntegration saasIntegration = new SaasIntegration();
  private final UserApi userApi = new UserApi();

  public UserApi getUserApi() {
    return userApi;
  }

  public SaasIntegration getSaasIntegration() {
    return saasIntegration;
  }

  public static class SaasIntegration {
    private String key;

    public String getKey() {
      return key;
    }

    public void setKey(String key) {
      this.key = key;
    }
  }

  public static class UserApi {
    private String createUserUrl;
    private String manageUserUrl;
    private String frontUrl;
    private String adminUrl;

    public String getManageUserUrl() {
      return manageUserUrl;
    }

    public void setManageUserUrl(String manageUserUrl) {
      this.manageUserUrl = manageUserUrl;
    }

    public String getFrontUrl() {
      return frontUrl;
    }

    public void setFrontUrl(String frontUrl) {
      this.frontUrl = frontUrl;
    }

    public String getAdminUrl() {
      return adminUrl;
    }

    public void setAdminUrl(String adminUrl) {
      this.adminUrl = adminUrl;
    }

    public String getCreateUserUrl() {
      return createUserUrl;
    }

    public void setCreateUserUrl(String createUserUrl) {
      this.createUserUrl = createUserUrl;
    }
  }
}
