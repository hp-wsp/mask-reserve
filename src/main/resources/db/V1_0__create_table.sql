#------------------------------------------------------------------------
#区域表
#------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS t_area (
  id INT(3) NOT NULL AUTO_INCREMENT,
  name VARCHAR(32) NOT NULL DEFAULT '' COMMENT '名称',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

#------------------------------------------------------------------------
#药店表
#------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS t_pharmacy (
  id INT(5) NOT NULL AUTO_INCREMENT,
  name VARCHAR(32) NOT NULL DEFAULT '' COMMENT '名称',
  area VARCHAR(32) NOT NULL DEFAULT '' COMMENT '区域',
  address VARCHAR(60) NOT NULL DEFAULT '' COMMENT '地址',
  stack INT(8) NOT NULL DEFAULT 0 COMMENT '库存',
  sell  INT(8) NOT NULL DEFAULT 0 COMMENT '预定量',
  version  INT(8) NOT NULL DEFAULT 0 COMMENT '版本号',
  update_time DATETIME NOT NULL COMMENT '修改时间',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;