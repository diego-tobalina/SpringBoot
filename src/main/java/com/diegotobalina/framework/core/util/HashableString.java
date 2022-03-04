package com.diegotobalina.framework.core.util;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.regex.Pattern;

/**
 * @author diegotobalina created on 13/08/2020
 */
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class HashableString {

  private String content;

  public HashableString(String content) {
    this.content = content;
  }

  /** Reemplaza el content de l objeto por el hash del mismo */
  public void hash(int strength) {
    if (isHashed()) return;
    var coder = new BCryptPasswordEncoder(strength);
    this.content = coder.encode(this.content);
  }

  /** Devuelve true en el caso de que el content sea un hash */
  public boolean isHashed() {
    var regex = "^\\$2[ayb]\\$.{56}$";
    var p = Pattern.compile(regex);
    var m = p.matcher(this.content);
    return m.matches();
  }

  /**
   * Devuelve true en el caso de que el content sea un hash y coincida con el hash del texto plano
   * pasado por parámetro
   */
  public boolean doHashMatch(String plainText) {
    if (!isHashed()) this.hash(6);
    var encoder = new BCryptPasswordEncoder();
    return encoder.matches(plainText, this.content);
  }
}
