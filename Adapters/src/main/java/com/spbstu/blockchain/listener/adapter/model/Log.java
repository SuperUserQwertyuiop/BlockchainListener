package com.spbstu.blockchain.listener.adapter.model;

import lombok.Value;

import java.sql.Timestamp;

@Value
public class Log {

    String hash;

    String info;

    Timestamp creationDate;

}
