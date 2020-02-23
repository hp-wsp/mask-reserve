#------------------------------------------------------------------------
#系统管理员表
#------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS t_manager (
  id CHAR(32) NOT NULL,
  username VARCHAR(30) NOT NULL COMMENT '用户名',
  password VARCHAR(50) NOT NULL COMMENT '密码',
  name VARCHAR(30) COMMENT '姓名',
  phone VARCHAR(20) DEFAULT '' COMMENT '联系电话',
  email VARCHAR(50) DEFAULT '' COMMENT '邮件地址',
  roles VARCHAR(200) DEFAULT '' NOT NULL COMMENT '权限角色',
  is_root TINYINT DEFAULT 0 NOT NULL COMMENT '1=超级用户',
  is_forbid TINYINT DEFAULT 1 NOT NULL COMMENT '1=禁用',
  is_delete TINYINT DEFAULT 0 NOT NULL COMMENT '1=删除',
  update_time DATETIME NOT NULL COMMENT '修改时间',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  UNIQUE KEY idx_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

#初始化系统管理员
INSERT INTO t_manager(id, username, password, name, roles, is_root, is_forbid, is_delete, update_time, create_time)
VALUES ('1', 'admin', '12345678', 'admin', "ROLE_SYS", 1, 0, 0, now(), now());
