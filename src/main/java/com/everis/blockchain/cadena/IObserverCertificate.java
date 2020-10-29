package com.everis.blockchain.cadena;

import java.io.IOException;

public interface IObserverCertificate {
    public void watchCertificates(Certificate cert) throws IOException;
}