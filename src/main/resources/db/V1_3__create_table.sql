#------------------------------------------------------------------------
#预定历史信息
#------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS t_his_reserve (
  id INT(8) NOT NULL AUTO_INCREMENT,
  id_card VARCHAR(20) NOT NULL COMMENT '身份证号',
  day INTEGER NOT NULL DEFAULT 0 COMMENT '预约天',
  PRIMARY KEY (id),
  UNIQUE KEY idx_id_card (id_card)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;