/**
 * Licensed under the GPL License. You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   https://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING,
 * WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE.
 */
package psiprobe.controllers.certificates;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.coyote.http11.AbstractHttp11JsseProtocol;
import org.apache.tomcat.util.net.SSLHostConfig;
import org.apache.tomcat.util.net.SSLHostConfigCertificate;

import psiprobe.model.certificates.CertificateInfo;
import psiprobe.model.certificates.ConnectorInfo;
import psiprobe.model.certificates.SSLHostConfigInfo;

/**
 * The Class SSLHostConfigHelper.
 */
public class SSLHostConfigHelper {

  /**
   * Instantiates a new SSL host config helper.
   *
   * @param protocol the protocol
   * @param info the info
   * @throws IllegalAccessException the illegal access exception
   * @throws InvocationTargetException the invocation target exception
   */
  public SSLHostConfigHelper(AbstractHttp11JsseProtocol<?> protocol, ConnectorInfo info)
      throws IllegalAccessException, InvocationTargetException {
    SSLHostConfig[] sslHostConfigs = protocol.findSslHostConfigs();
    List<SSLHostConfigInfo> sslHostConfigInfos = new ArrayList<>(sslHostConfigs.length);
    info.setSslHostConfigInfos(sslHostConfigInfos);

    for (SSLHostConfig sslHostConfig : sslHostConfigs) {
      sslHostConfigInfos.add(toSslHostConfigInfo(sslHostConfig));
    }
  }

  /**
   * To SSLHostConfig info.
   * 
   * @param sslHostConfig the SSLHostConfig
   * @return the SSLHostConfig info
   * @throws IllegalAccessException the illegal access exception
   * @throws InvocationTargetException the invocation target exception
   */
  private SSLHostConfigInfo toSslHostConfigInfo(SSLHostConfig sslHostConfig)
      throws IllegalAccessException, InvocationTargetException {
    SSLHostConfigInfo sslHostConfigInfo = new SSLHostConfigInfo();
    BeanUtils.copyProperties(sslHostConfigInfo, sslHostConfig);

    Set<SSLHostConfigCertificate> certificates = sslHostConfig.getCertificates();
    List<CertificateInfo> certificateInfos = new ArrayList<>(certificates.size());
    sslHostConfigInfo.setCertificateInfos(certificateInfos);
    for (SSLHostConfigCertificate sslHostConfigCertificate : certificates) {
      certificateInfos.add(toCertificateInfo(sslHostConfigCertificate));
    }

    return sslHostConfigInfo;
  }

  /**
   * To certificate info.
   * 
   * @param sslHostConfigCertificate the SSLHostConfigCertificate
   * @return the certificate info
   * @throws IllegalAccessException the illegal access exception
   * @throws InvocationTargetException the invocation target exception
   */
  private CertificateInfo toCertificateInfo(SSLHostConfigCertificate sslHostConfigCertificate)
      throws IllegalAccessException, InvocationTargetException {
    CertificateInfo certificateInfo = new CertificateInfo();
    BeanUtils.copyProperties(certificateInfo, sslHostConfigCertificate);
    return certificateInfo;
  }
}