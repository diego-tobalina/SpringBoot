package com.diegotobalina.framework.provided.multitenant.tenantdatasource.domain;

import com.diegotobalina.framework.provided.encyption.EncryptAttribute;
import com.diegotobalina.framework.provided.interfaces.entity.CustomEntity;
import com.diegotobalina.framework.provided.interfaces.entity.CustomEntityListener;
import lombok.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Audited
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TENANT_DATA_SOURCE")
@FilterDef(
    name = "tenantFilter",
    parameters = {@ParamDef(name = "tenantId", type = "string")})
@Filter(name = "tenantFilter", condition = "tenant_id = :tenantId")
@EntityListeners({CustomEntityListener.class, TenantDataSourceListener.class})
public class TenantDataSource extends CustomEntity {

  @Id
  @GeneratedValue
  @Column(name = "ID")
  protected Long id;

  private String name;
  private String description;
  private String headerToken;

  @Convert(converter = EncryptAttribute.class)
  private String url;

  @Convert(converter = EncryptAttribute.class)
  private String username;

  @Convert(converter = EncryptAttribute.class)
  private String password;

  private String driverClassName;
  private boolean active;

  @Override
  public String getNotValidCause() {
    return null;
  }
}
