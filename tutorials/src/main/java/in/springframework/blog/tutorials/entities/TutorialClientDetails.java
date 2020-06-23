package in.springframework.blog.tutorials.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "clients",
        uniqueConstraints =
                {
                        @UniqueConstraint(name = "uq_clientId", columnNames = {"clientId"})
                })
@EntityListeners(AuditingEntityListener.class)
public class TutorialClientDetails implements ClientDetails {

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public Set<String> getResourceIds() {
    return resourceIds;
  }

  public void setResourceIds(Set<String> resourceIds) {
    this.resourceIds = resourceIds;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
  }

  public Set<String> getScope() {
    return scope;
  }

  public void setScope(Set<String> scope) {
    this.scope = scope;
  }

  public Set<String> getAuthorizedGrantTypes() {
    return authorizedGrantTypes;
  }

  public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
    this.authorizedGrantTypes = authorizedGrantTypes;
  }

  public Set<String> getWebServerRedirectUri() {
    return webServerRedirectUri;
  }

  public void setWebServerRedirectUri(Set<String> webServerRedirectUri) {
    this.webServerRedirectUri = webServerRedirectUri;
  }

  public void setAuthorities(Set<GrantedAuthority> authorities) {
    this.authorities = authorities;
  }

  public int getAccessTokenValidity() {
    return accessTokenValidity;
  }

  public void setAccessTokenValidity(int accessTokenValidity) {
    this.accessTokenValidity = accessTokenValidity;
  }

  public int getRefreshTokenValidity() {
    return refreshTokenValidity;
  }

  public void setRefreshTokenValidity(int refreshTokenValidity) {
    this.refreshTokenValidity = refreshTokenValidity;
  }

  public void setAdditionalInformation(Map<String, String> additionalInformation) {
    this.additionalInformation = additionalInformation;
  }

  public boolean isAutoApprove() {
    return autoApprove;
  }

  public void setAutoApprove(boolean autoApprove) {
    this.autoApprove = autoApprove;
  }

  @Override
  public boolean isSecretRequired() {
    return clientSecret != null;
  }

  @Override
  public boolean isScoped() {
    return scope != null && !scope.isEmpty();
  }

  @Override
  public Set<String> getRegisteredRedirectUri() {
    return webServerRedirectUri;
  }

  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    return authorities.stream().collect(Collectors.toSet());
  }

  @Override
  public Integer getAccessTokenValiditySeconds() {
    return accessTokenValidity;
  }

  @Override
  public Integer getRefreshTokenValiditySeconds() {
    return refreshTokenValidity;
  }

  @Override
  public boolean isAutoApprove(String s) {
    return autoApprove;
  }

  public Map<String, Object> getAdditionalInformation() {
    Map<String, Object> returnMap = new HashMap<>();
    additionalInformation.forEach((k,v) -> returnMap.put(k, v));
    return returnMap;
  }

  @Id
  private String clientId;
  @ElementCollection(fetch = FetchType.EAGER)
  private Set<String> resourceIds;
  private String clientSecret;
  @ElementCollection(fetch = FetchType.EAGER)
  private Set<String> scope;
  @ElementCollection(fetch = FetchType.EAGER)
  private Set<String> authorizedGrantTypes;
  @ElementCollection(fetch = FetchType.EAGER)
  private Set<String> webServerRedirectUri;
  @ElementCollection(fetch = FetchType.EAGER)
  private Set<GrantedAuthority> authorities;
  private int accessTokenValidity;
  private int refreshTokenValidity;
  @ElementCollection(fetch = FetchType.LAZY)
  private Map<String, String> additionalInformation;
  private boolean autoApprove;
}
