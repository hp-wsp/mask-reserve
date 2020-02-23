#------------------------------------------------------------------------
#药店表
#------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS t_reserve (
  id INT(9) NOT NULL AUTO_INCREMENT,
  tra_id VARCHAR(12) NOT NULL DEFAULT '' COMMENT '交易编号',
  name VARCHAR(32) NOT NULL DEFAULT '' COMMENT '姓名',
  address VARCHAR(60) NOT NULL DEFAULT '' COMMENT '地址',
  mobile VARCHAR(20) NOT NULL DEFAULT '' COMMENT '手机',
  pha_id INT(5) NOT NULL COMMENT '药店编号',
  pharmacy VARCHAR(60) NOT NULL DEFAULT '' COMMENT '药店',
  pha_address VARCHAR(60) NOT NULL DEFAULT '' COMMENT '药店地址',
  id_card VARCHAR(20) NOT NULL DEFAULT '' COMMENT '身份证编号',
  cycle INTEGER NOT NULL DEFAULT 0 COMMENT '预约周期',
  count INT(2) NOT NULL DEFAULT 0 COMMENT '预定个数',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  INDEX idx_pharmacy (pharmacy),
  INDEX idx_id_card (id_card)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

#------------------------------------------------------------------------
#交易序号
#------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS t_tra_id (
  id INT(8) NOT NULL AUTO_INCREMENT,
  pha_id INT(5) NOT NULL COMMENT '药店编号',
  day INT(6) NOT NULL DEFAULT 0 COMMENT '天',
  count INT(5) NOT NULL DEFAULT 0 COMMENT '序号',
  PRIMARY KEY (id),
  UNIQUE KEY idx_pha_id_count (pha_id, count)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;