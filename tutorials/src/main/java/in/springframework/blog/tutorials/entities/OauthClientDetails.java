package in.springframework.blog.tutorials.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "oauth_client_details")
public class OauthClientDetails {

  @Id
  @Column(name = "client_id")
  private String clientId;
  @Column(name = "resource_ids")
  private String resourceIds;
  @Column(name = "client_secret")
  private String clientSecret;
  private String scope;
  @Column(name = "authorized_grant_types")
  private String authorizedGrantTypes;
  @Column(name = "web_server_redirect_uri")
  private String webServerRedirectUri;
  private String authorities;
  @Column(name = "access_token_validity")
  private int accessTokenValidity;
  @Column(name = "refresh_token_validity")
  private int refreshTokenValidity;
  @Column(name = "additional_information")
  private String additionalInformation;
  @Column(name = "autoapprive")
  private String autoApprove;

}
