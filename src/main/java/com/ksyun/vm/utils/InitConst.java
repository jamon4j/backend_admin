package com.ksyun.vm.utils;

public class InitConst {

	public static final String ADMIN = "admin";
	public static final String PASSWORD = "ksc";
	public static final String BACKEND = "backend";
	public static final String HTTP_COOKIE = "kscdigest";

	public static final String HTTP_HOST = "http.host";
	public static final String HTTP_PORT = "http.port";

	public static final String HTTP_HOST_PUBLIC = "http.host.public";
	public static final String HTTP_PORT_PUBLIC = "http.port.public";
	public static final String HTTP_HOST_PRIVATE = "http.host.private";
	public static final String HTTP_PORT_PRIVATE = "http.port.private";

	public static final String HTTP_HOST_ICONSOLE = "http.host.iconsole";
	public static final String HTTP_PORT_ICONSOLE = "http.port.iconsole";

	public static final String KVM_ZONE_LIST = "kvm.zone.list";
	public static final String KVM_ZONE_HOST = "kvm.zone.host";
	public static final String KVM_ZONE_HOST_LIST = "kvm.zone.host.list";

	public static final String KVM_HOST_VM_LIST = "kvm.host.vm.list";

	public static final String KVM_IMAGE_LIST = "kvm.image.list";

	public static final String KVM_USER_TOKEN = "kvm.user.auth";
	public static final String KVM_USER_REGISTER = "kvm.user.register";
	public static final String KVM_USER_LIST = "kvm.user.list";
	public static final String KVM_USER = "kvm.user";

	public static final String KVM_EBS = "kvm.ebs";
	public static final String KVM_EBS_CREATE = "kvm.ebs.create";
	public static final String KVM_EBS_DELETE = "kvm.ebs.delete";
	public static final String KVM_EBS_ATTACH = "kvm.ebs.attach";
	public static final String KVM_EBS_DETACH = "kvm.ebs.detach";
	public static final String KVM_EBS_USER_LIST = "kvm.ebs.user.list";
	public static final String KVM_EBS_VM_LIST = "kvm.ebs.vm.list";

	public static final String KVM_VM = "kvm.vm";
	public static final String KVM_VM_LIST = "kvm.vm.list";
	public static final String KVM_VM_LIST_ALL = "kvm.vm.list.all";
	public static final String KVM_VM_START = "kvm.vm.start";
	public static final String KVM_VM_CREATE = "kvm.vm.create";
	public static final String KVM_VM_DELETE = "kvm.vm.delete";
	public static final String KVM_VM_STOP = "kvm.vm.stop";
	public static final String KVM_VM_RESTART = "kvm.vm.restart";
	public static final String KVM_VM_PWD = "kvm.vm.pwd";
	public static final String KVM_VM_COLDMOVE = "kvm.vm.coldmove";
	public static final String KVM_VM_RESET = "kvm.vm.reset";

	public static final String KVM_SECURITY_GROUP = "kvm.sg";
	public static final String KVM_SECURITY_GROUP_LIST = "kvm.sg.list";
	public static final String KVM_SECURITY_GROUP_CRATE = "kvm.sg.create";
	public static final String KVM_SECURITY_GROUP_DELETE = "kvm.sg.delete";
	public static final String KVM_SECURITY_GROUP_RULE_CREATE = "kvm.sg.rule.create";
	public static final String KVM_SECURITY_GROUP_RULE_DELETE = "kvm.sg.rule.delete";
	public static final String KVM_SECURITY_GROUP_ATTACH = "kvm.sg.attach";
	public static final String KVM_SECURITY_GROUP_DETTACH = "kvm.sg.detach";

	public static final String KVM_SNAPSHOT_VM_LIST = "kvm.snapshot.vm.list";
	public static final String KVM_SNAPSHOT_VM_CREATE = "kvm.snapshot.vm.create";
	public static final String KVM_SNAPSHOT_VM_DELETE = "kvm.snapshot.vm.delete";
	public static final String KVM_SNAPSHOT_EBS_LIST = "kvm.snapshot.ebs.list";
	public static final String KVM_SNAPSHOT_EBS_CREATE = "kvm.snapshot.ebs.create";
	public static final String KVM_SNAPSHOT_EBS_DELETE = "kvm.snapshot.ebs.delete";

	public static final String KVM_STAT_ZONE = "kvm.stat.zone";
	public static final String KVM_STAT_HOST = "kvm.stat.host";
	public static final String KVM_STAT_IP = "kvm.stat.ip";

	public static final String KVM_VNC = "kvm.vnc";

	public static final String KVM_BANDWIDTH = "kvm.bandwidthtune";

	public static final String KVM_LBS_POOL_LIST = "kvm.lbs.pool.list";
	public static final String KVM_LBS_POOL = "kvm.lbs.pool";
	public static final String KVM_LBS_POOL_ADD = "kvm.lbs.pool.add";
	public static final String KVM_LBS_POOL_DELETE = "kvm.lbs.pool.delete";
	public static final String KVM_LBS_POOL_UPDATE_NAME = "kvm.lbs.pool.update.name";
	public static final String KVM_LBS_POOL_UPDATE_EGRESS = "kvm.lbs.pool.update.egress";
	public static final String KVM_LBS_POOL_UPDATE_OPEN = "kvm.lbs.pool.update.open";
	public static final String KVM_LBS_VIP_LIST = "kvm.lbs.vip.list";
	public static final String KVM_LBS_VIP = "kvm.lbs.vip";
	public static final String KVM_LBS_VIP_ADD = "kvm.lbs.vip.add";
	public static final String KVM_LBS_VIP_DELETE = "kvm.lbs.vip.delete";
	public static final String KVM_LBS_VIP_UPDATE_NAME = "kvm.lbs.vip.update.name";
	public static final String KVM_LBS_VIP_UPDATE_OPEN = "kvm.lbs.vip.update.open";
	public static final String KVM_LBS_VIP_UPDATE_CONNECTION_LIMIT = "kvm.lbs.vip.update.connection_limit";
	public static final String KVM_LBS_VIP_UPDATE_SESSION_PERSISTENCE = "kvm.lbs.vip.update.session_persistence";
	public static final String KVM_LBS_MEMBER_LIST = "kvm.lbs.member.list";
	public static final String KVM_LBS_MEMBER = "kvm.lbs.member";
	public static final String KVM_LBS_MEMBER_ADD = "kvm.lbs.member.add";
	public static final String KVM_LBS_MEMEBER_DELETE = "kvm.lbs.member.delete";
	public static final String KVM_LBS_MEMBER_UPDATE_WEIGHT = "kvm.lbs.member.update.weight";
	public static final String KVM_LBS_MEMBER_UPDATE_OPEN = "kvm.lbs.member.update.open";
	public static final String KVM_LBS_HEALTH_LIST = "kvm.lbs.health.list";
	public static final String KVM_LBS_HEALTH = "kvm.lbs.health";
	public static final String KVM_LBS_HEALTH_ADD = "kvm.lbs.health.add";
	public static final String KVM_LBS_HEALTH_DELETE = "kvm.lbs.health.delete";
	public static final String KVM_LBS_HEALTH_BIND = "kvm.lbs.health.bind";
	public static final String KVM_LBS_HEALTH_UNBIND = "kvm.lbs.health.unbind";
	public static final String KVM_LBS_HEALTH_UPDATE_TIMEOUT = "kvm.lbs.health.update.timeout";
	public static final String KVM_LBS_HEALTH_UPDATE_DELAY = "kvm.lbs.health.update.delay";
	public static final String KVM_LBS_HEALTH_UPDATE_MAX_RETRIES = "kvm.lbs.health.update.max_retries";
	public static final String KVM_LBS_HEALTH_UPDATE_HTTP_METHOD = "kvm.lbs.health.update.http_method";
	public static final String KVM_LBS_HEALTH_UPDATE_URL_PATH = "kvm.lbs.health.update.url_path";
	public static final String KVM_LBS_HEALTH_UPDATE_RISE = "kvm.lbs.health.update.rise";
	public static final String KVM_LBS_HEALTH_FALL = "kvm.lbs.health.update.fall";
	public static final String KVM_LBS_HEALTH_OPEN = "kvm.lbs.health.update.open";


    public static final String KVM_RDS_INSTANCE_LIST = "kvm.rds.instance.list";
    public static final String KVM_RDS_INSTANCE_GET = "kvm.rds.instance.get";
    public static final String KVM_RDS_INSTANCE_ADD = "kvm.rds.instance.add";
    public static final String KVM_RDS_INSTANCE_DEL = "kvm.rds.instance.del";
    public static final String KVM_RDS_INSTANCE_BACKUP_CONFIG = "kvm.rds.instance.backup_config";
    public static final String KVM_RDS_INSTANCE_ADMIN_USER = "kvm.rds.instance.admin_user";
    public static final String KVM_RDS_INSTANCE_BACKUPS = "kvm.rds.instance.backups";

    public static final String KVM_RDS_INSTANCE_BACKUP_CONFIG_MODIFY = "kvm.rds.instance.backup_config_modify";
    public static final String KVM_RDS_INSTANCE_RESET_ADMIN_PASSWORD = "kvm.rds.instance.reset_admin_password";
    public static final String KVM_RDS_INSTANCE_UPGRADE_HA = "kvm.rds.instance.upgrade_ha";
    public static final String KVM_RDS_INSTANCE_RESIZE = "kvm.rds.instance.resize";
    public static final String KVM_RDS_INSTANCE_RESTART = "kvm.rds.instance.restart";
    public static final String KVM_RDS_INSTANCE_MIGRATE = "kvm.rds.instance.migrate";
    public static final String KVM_RDS_INSTANCE_FAILOVER = "kvm.rds.instance.failover";

    public static final String KVM_RDS_SERVICEIMAGE_LIST = "kvm.rds.serviceimage.list";

    public static final String KVM_RDS_FLAVOR_LIST = "kvm.rds.flavor.list";
    public static final String KVM_RDS_FLAVOR_GET = "kvm.rds.flavor.get";

    public static final String KVM_RDS_BACKUP_ADD = "kvm.rds.backup.add";
    public static final String KVM_RDS_BACKUP_GET = "kvm.rds.backup.get";
    public static final String KVM_RDS_BACKUP_LIST = "kvm.rds.backup.list";
    public static final String KVM_RDS_BACKUP_DEL = "kvm.rds.backup.del";

    public static final String KVM_RDS_SECURITYGROUP_GET = "kvm.rds.securitygroup.get";
    public static final String KVM_RDS_SECURITYGROUP_LIST = "kvm.rds.securitygroup.list";
    public static final String KVM_RDS_SECURITYGROUPRULE_ADD = "kvm.rds.securitygrouprule.add";
    public static final String KVM_RDS_SECURITYGROUPRULE_DEL = "kvm.rds.securitygrouprule.del";


	public static final String MONITOR_HBASE_TABLE_STATUS = "vm_table_status";
	public static final String MONITOR_HBASE_TABLE_NETWORK = "vm_table_network";
	public static final String MONITOR_HBASE_TABLE_LOAD = "vm_table_load";
	public static final String MONITOR_HBASE_TABLE_DISK = "vm_table_disk";

	public static final String ICONSOLE_UPDATE_EGRESS = "iconsole.update.egress";

	public static final String COOKIE_NOW_NAME = "now";
	public static final String COOKIE_PUBLIC_NAME = "allow_public";
	public static final String COOKIE_PRIVATE_NAME = "allow_private";
	public static final String COOKIE_PUBLIC = "public";
	public static final String COOKIE_PRIVATE = "private";
	public static final String COOKIE_SPLIT = "&3B87652BA0916C03C634D5DB8558D494&";


    public static final String CREATE_RDS_URL = "kvm.rds.callback";
}
