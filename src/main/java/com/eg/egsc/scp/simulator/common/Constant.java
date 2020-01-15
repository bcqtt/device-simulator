/**
 * Copyright 2017-2025 Evergrande Group.
 */

package com.eg.egsc.scp.simulator.common;

/**
 * 常量类.
 * 
 * @author zhangweixian
 * @since 2018年1月11日
 */
public class Constant {
  /**
   * <p>
   * Description: 默认构造方法.
   * </p>
   */
  private Constant() {}

  // ********************** Redis key 常量 start **********************/
  // Reds缓存所有设备管理下发的设备
  public static final String REDIS_KEY_DEVICE_ALL = "DEVICE:ALL";
  // Reds缓存设备ID
  public static final String REDIS_KEY_DEVICE_ID = "DEVICE:ID:";
  // Reds缓存包号
  public static final String REDIS_KEY_MSGID_FORMAT = "DEVICE:%s:PACKAGENO:%s";
  // Redis缓存traceId
  public static final String REDIS_KEY_TRACEID = "DEVICE:%s:TRACEID:%s";
  // Redis缓存spanId
  public static final String REDIS_KEY_SPANID = "DEVICE:%s:SPANID:%s";
  // Redis缓存设备的sessionId
  public static final String REDIS_KEY_SESSIONID = "DEVICE:%s:SESSIONID";
  // Redis缓存正在进行充电的智能插座列表
  public static final String REDIS_KEY_DEVICE_CHARGINGBOX_ID = "DEVICE:CHARGINGBOX";
  // Redis缓存正在进行充电的智能插座的操作类型
  public static final String REDIS_KEY_DEVICE_CHARGINGBOX_OP_TYPE = "DEVICE:%s:CHARGINGBOX:OPTYPE";
  // Redis缓存正在进行充电的智能插座设备所用电量（多少度）
  public static final String REDIS_KEY_DEVICE_CHARGINGBOX_KWH = "DEVICE:%s:CHARGINGBOX:KWH";
  // Redis缓存开始充电时开始时间
  public static final String REDIS_KEY_CHARGING_START_TIME = "DEVICE:%s:CHARGING:START:TIME";
  // Reds缓存随机秘钥
  public static final String REDIS_KEY_AESKEY = "DEVICE:AESKEY:";
  // Reds缓存单个设备单个事件
  public static final String REDIS_KEY_DEVICE_EVENT = "DEVICE:%s:EVENT:%d";
  // Reds缓存 单个设备单个事件的最后一次上报事件
  public static final String LastEventTime = "LastEventCount";
  // Reds缓存 单个设备单个事件在频率限制的时间内（比如500毫秒内）上报该事件的次数
  public static final String EventCountInLimitTime = "EventCountInLimitTime";
  // Reds缓存消息序号
  public static final String REDIS_KEY_MSG_SEQUENCE = "COMMON:MSG_SEQUENCE";
  //
  // Reds缓存SessionID锁
  public static final String REDIS_KEY_LOCK_DEVICE_SESSIONID = "DEVICE:%s:LOCK:SESSIONID";
  
  // 智能电表 帧起始标志
  public static final byte MeterFrameStartFlag = (byte)0x68;
  // 智能电表 帧结束标志
  public static final byte MeterFrameEndFlag = (byte)0x16;
  
  //
  /*
  //  Reds缓存 单个设备限流（每秒最多多少次上报）
  public static final String REDIS_KEY_DEVICE_IN_RATE_LIMIT = "DEVICE:%s:IN_RATE_LIMIT:%s";
  //  Reds缓存 单个设备限流（每秒最多多少次上报），加上COMMAND和UUID区分每一次上报
  public static final String REDIS_KEY_DEVICE_IN_RATE_LIMIT_COMMAND = "COMMAND:%s:%s";
  //  Reds缓存 单个设备限流（超过每秒限流次数的上报）
  public static final String REDIS_KEY_DEVICE_OUT_OF_RATE_LIMIT = "DEVICE:%s:OUT_OF_RATE_LIMIT:%s";
  //  Reds缓存 单个设备限流（超过每秒限流次数的上报），加上COMMAND和UUID区分每一次上报
  public static final String REDIS_KEY_DEVICE_OUT_OF_RATE_LIMIT_COMMAND = "COMMAND:%s:%s";
  */
  //Reds缓存 锁，设备管理广播设备列表维护单个设备
//  public static final String REDIS_LOCK_DEVICE_MGT = "REDIS_LOCK_DEVICE_MGT:DEVICE-%s";
  //Reds缓存 锁，从设备管理初始化设备列表
//  public static final String REDIS_LOCK_INIT_DEVICE_LIST = "REDIS_LOCK_INIT_DEVICE_LIST";
  //Reds缓存 锁，单个设备限流，设备上报超限后告警，加锁防止不断告警
  public static final String REDIS_LOCK_DEVICE_OUT_OF_RATE_LIMIT_ALARM = "DEVICE:%s:OUT_OF_RATE_LIMIT_ALARM";
  // ********************** Redis key 常量 end **********************/

  // ********************** 充电插座 start **********************/
  public static final int CHARGE_BOX_SWITCH_ON = 1;
  public static final int CHARGE_BOX_SWITCH_OFF = 0;
  
  // ********************** 充电插座 end **********************/
  

  // ********************** 其他常量 end **********************/
  //Reds缓存 锁的时间
  public static final long LOCK_EXPIRE_TIME_SECONDS = 30;
  //  单个设备限流（每秒最多多少次上报）
  public static final String DEVICE_RATE_LIMIT = "DEVICE:%s:RATE_LIMIT";
  // ********************** 其他常量 end **********************/
  
  // 注册设备命令
  public static final String REGISTER_TIME = "registerTime";
  // 事件ID
  public static final String EVENT_TYPE_ID = "eventTypeID";
  // 接入系统标识
  public static final String APP_ID = "appID";
  // 接入系统认证
  public static final String TOKEN = "token";
  // 设备编号
  public static final String DEVICEID = "10012025123456781000";
  // 设备编号
  public static final String DEVICE_ID = "deviceID";
  // 父设备编号
  public static final String PARENT_ID = "parentID";
  // 事件组消息简称
  public static final String TOPIC = "topic";
  // 消息编号:要求网关生成一个uuid
  public static final String MESSAGE_ID = "messageID";
  // 时间戳
  public static final String TIMESTAMP = "timestamp";

  // 0-起始，1-脉冲，2-结束， 3-不适用 使用视频设备
  public static final String EVENT_STATUS = "eventStatus";
  // No：不需要回执, 为缺省设置；Yes：需要回执；Failure：失败时需要回执
  public static final String REPLY_FLAG = "replyFlag";
  // 回执标志默认值
  public static final String GATEWAY_REPLY_FLAG_DEFUALTVALUE = "no";
  // 指定回复消息的目标队列
  public static final String REPLY_TO_QUEUE = "replyToQueue";
  // 同步调用模式下需要的关联ID
  public static final String CORRELATION_ID = "correlationID";
  // 网关标识
  public static final String GATEWAY_ID = "gatewayID";
  // 网关类型标识
  public static final String GATEWAY_TAG = "gatewayType";
  // 网关类型
  public static final String GATEWAYTYPE = "4";// gatewayType     1 ： 国标网关,   2 ： 轻量级网关,   3 ： 广播网关, 4: 充电网关
  // （0-默认上下行消息，1-上行回复消息）
  public static final String IS_REPLY_MSG = "isReplyMsg";
  public static final String PAY_LOAD = "payload";
  // 设备列表
  public static final String DEVICE_LIST = "deviceList";
  // 是否在离线
  public static final String IS_ON_LINE = "isOnline";
  // 时间
  public static final String TIME = "time";
  // 网关服务ID
  public static final String GATEWAY_SERVICE_ID = "gatewayServiceID";
  // 默认命令
  public static final String DEFAULT = "DEFAULT";
  // 设备发给网关的数据
  public static final String DATA = "Data";
  // 命令常量
  public static final String COMMAND = "Command";
  // 设备发给网关的事件ID
  public static final String RECORD_TYPE = "RecordType";
  // 逆向转换方法
  public static final String RECONVERT = "reconvert";
  // 正向转换方法
  public static final String CONVERT = "convert";

  // AESkey值
  public static final String AESKEY = "aeskey";

  // 请求应答标志：1字节，0 请求，1应答  
  public static final String FLAG_REQUEST = "0";
  // 请求应答标志：1字节，0 请求，1应答  
  public static final String FLAG_RESPONSE = "1";

  public static final int HEARTBEAT_MAX_TIME = 120*1000;
  // *************** 测试单元需要的常量 ******************/
  // 版本
  public static final String VERSION = "HDXM";
  // 门禁源ID
  public static final String DOORSRCID = "1004201658FCDBD8341E";
  // 门禁目标ID
  public static final String DOORDESTID = "00000000000000000000";
  // 包号
  public static final String PACKAGENO = "172";
  // CRC
  public static final String CRC16 = "42027";
  // 预留字段
  public static final String HOLD = "00";
  // 网关ID
//  public static final String GATEWAY_TEST_ID = "APP1961";// APP0152

  // 车场源ID
  public static final String PARKSRCID = "1003200500189901087F";

  // 编码集
  public static final String ENCODING = "utf-8";

  // 响应总线状态
  public static final String SUCCESS = "SUCCESS";
  public static final String FAILURE = "FAILURE";

  // 响应客户端状态
  public static final Integer SUCCESS_FLAG = 0; // 0，成功
  public static final Integer FAILURE_FLAG = 1; // 1，失败

  // ************************ MQ常超设置 **********************/
  // 轻量级网关token
//  public static final String GATEWAY_TOKENX = "23edf6db0a17cc75";// qq9cJYpWfIZRUj2Z
  // 网关作为目的ID
  public static final String GATEWAY_DEVICE_ID_PRE = "0000000000000"; // + APP1961  13+7 共20个字符

  // *********************** 解码编码类中常量 *******************************/
  // 协议头
  public static final String PROTOCOL = "protocol";
  // 分包时已读内容
  public static final String PROTOCOL_PREFIXCONTEXT = "prefixContext";
  // 分包时总长度
  public static final String PROTOCOL_TOTALLENGTH = "totalLength";
  // 分包时己读长度
  public static final String PROTOCOL_READLENGTH = "readLength";

  // ****************************** 事件类型编码范围 ****************************/
  // 停车场范围
  public static final Integer PARKING_EVENTTYPEID_START = 0;
  public static final Integer PARKING_EVENTTYPEID_END = 19999;
  // 门禁范围
  public static final Integer DOORS_EVENTTYPEID_START = 30000;
  public static final Integer DOORS_EVENTTYPEID_END = 39999;
  // 梯控范围
  public static final Integer LIFTCONTROL_EVENTTYPEID_START = 50000;
  public static final Integer LIFTCONTROL_EVENTTYPEID_END = 59999;
  // 广告屏范围
  public static final Integer INFOSCREEN_EVENTTYPEID_START = 60000;
  public static final Integer INFOSCREEN_EVENTTYPEID_END = 69999;
  // 巡更范围
  public static final Integer PATROL_EVENTTYPEID_START = 70000;
  public static final Integer PATROL_EVENTTYPEID_END = 79999;
  // 电子围栏
  public static final Integer ELETRIC_FENCE_EVENTTYPEID_START = 91001;
  public static final Integer ELETRIC_FENCE_EVENTTYPEID_END = 93000;
  // 电子车位控制
  public static final Integer ELETRIC_CARPORTCONTROLLER_EVENTTYPEID_START = 93001;
  public static final Integer ELETRIC_CARPORTCONTROLLER_EVENTTYPEID_END = 95000;
  // 设备管理范围
  public static final Integer DEVICE_EVENTTYPEID_START = 90000;
  public static final Integer DEVICE_EVENTTYPEID_END = 99999;
  // 充电桩
  public static final Integer CHARGE_EVENTTYPEID_START = 95000;
  public static final Integer CHARGE_EVENTTYPEID_END = 95999;
  
  // ********************** 常用的事件类型编号 *******************************/
  // 上报设备参数获取响应事件类型编号
  public static final Integer DEVICE_PARAMTER_RESPONE = 90008;
  // 上报设备参数配置响应事件类型编号
  public static final Integer DEVICE_PARAMTER_CONFIG_RESPONE = 90009;
  // 设备是否在离线事件类型编号,上报设备离在线状态
  public static final Integer COM_UPLOAD_DEVICE_STATUS = 90010;
  // 下发设备列表事件类型编号
  public static final Integer DEVICE_LIST_DOWNLOAD = 90001;
  // 设备注册信息上报事件类型编号
  public static final Integer DEVICE_INFO_REGISTER = 90006;
  // 设备列表请求事件类型编号
  public static final Integer DEVICE_LIST_REQUEST = 90007;
  // 子设备信息上报事件类型编号
  public static final Integer COM_UPLOAD_SUB_DEV = 90011;
  // 上报设备离异常状态
  public static final Integer COM_UPLOAD_DEVICE_STATUS_EX = 90015;

  // ********************** 巡更常量begin *******************************/
  // data字段
  public static final String PAT_DATA = "data";
  // 组件下发中的任务key
  public static final String PAT_BUS_TASKID = "uuid";
  // 下发设备中的任务key
  public static final String PAT_TASKID = "taskID";
  // 组件下发中的设备key
  public static final String PAT_BUS_DEVICE_ID = "deviceId";
  // 下发设备中的设备key（已存在）DEVICE_ID
  // startTime字段
  public static final String PAT_STARTTIME = "startTime";
  // endTime字段
  public static final String PAT_ENDTIME = "endTime";
  // planTime字段
  public static final String PAT_PLANTIME = "planTime";
  // pointTime字段
  public static final String PAT_POINTTIME = "pointTime";
  // 组件下发中的巡更点key
  public static final String PAT_BUS_PATROLHISTORYPOINTS = "patrolHistoryPoints";
  // 下发设备中的巡更点key
  public static final String PAT_PATROLHISTORYPOINTS = "pointArray";
  // 组件下发中的经度key
  public static final String PAT_BUS_LONGITUDE = "lng";
  // 下发设备中的经度key
  public static final String PAT_LONGITUDE = "longitude";
  // 组件下发中的维度key
  public static final String PAT_BUS_LATITUDE = "lat";
  // 下发设备中的维度key
  public static final String PAT_LATITUDE = "latitude";
  // 组件下发中的结果类型key
  public static final String PAT_BUS_RESULTTYPE = "historyPointStatus";
  // 下发设备中的结果类型key
  public static final String PAT_RESULTTYPE = "resultType";
  // 组件下发中的巡更点编号key
  public static final String PAT_BUS_TASKPOINTID = "uuid";
  // 下发设备中的巡更点编号key
  public static final String PAT_TASKPOINTID = "taskPointID";
  // 请求标识常量requestTag
  public static final String PAT_REQUESTTAG = "requestTag";
  // 下发设备中eventTime
  public static final String PAT_EVENTTIME = "eventTime";
  // 下发设备中sendTime
  public static final String PAT_SENDTIME = "sendTime";
  // 下发设备中finishTime
  public static final String PAT_FINISHTIME = "finishTime";
  // 下发设备中type
  public static final String PAT_TYPE = "type";
  // 上报结果状态-成功
  public static final String TYPE_FAIL = "1";
  // 执行反馈下发—command
  public static final String PAT_COMMAND = "command";
  // ********************** 单元测试DTO常量begin *******************************/
  // 单元测试 BASEDTO 设备ID
  public static final String BASE_DEVICE_ID = "1004201658FCDBD8341C";
  // 单元测试 BASEDTO 父设备ID
  public static final String BASE_PARENT_ID = "1004201658FCDBD8341F";
  // 单元测试 BusDeviceDataDto 父设备ID
  public static final String BUS_DEVICE_DATA_COMMAND = "PAK_CONTROL_LOCK";
  // 测试字符串
  public static final String STRING_TEST = "request";
  // 测试correlationID
  public static final String CORRELATION_ID_DTO = "0153966f23df43269f9741678ffbd91d";
  // 测试isReplyMsg
  public static final Integer IS_REPLY_MSG_DTO = 0;
  // 测试messageID
  public static final String MESSAGE_ID_DTO = "0153966f23df43269f9741678ffbd91f";
  // 测试replyFlag
  public static final String REPLY_FLAG_DTO = "1";
  // 测试Timestamp
  public static final String TIME_STAMP = "2018-02-28 09:06:08.000";
  // 测试Topic
  public static final String TOPIC_DTO = "MSG/EVENT/PARKING";
  // 测试整数
  public static final Integer INTEGER_TEST = 1;
  // 测试CRC16
  public static final String CRC_16 = "35439";
  // 测试DevicePORT
  public static final String DEVICE_PORT = "8080";
  // 测试DeviceTypeCode
  public static final String DEVICE_TYPE_CODE = "2005";
  // 测试布尔值
  public static final Boolean BOOLEAN_DTO = true;
  // 测试Double
  public static final Double DOUBLE_DTO = 12.123;
  // 测试整形时间
  public static final Long TIME_DTO = 24253306L;
  // 测试整形时间
  public static final Integer TIME_INT_DTO = 24253306;
  // 测试result
  public static final String RESULT = "1";
  // 测试result2
  public static final Integer RESULT2 = 1;
  // 测试userID
  public static final String USER_ID = "387afb997b134d3b8f559522d9ae2466";
  // 测试userID
  public static final Integer USER_TYPE = 1;
  // 测试recordType
  public static final Integer RECORD_TYPE_DTO = 1;
  // 测试url
  public static final String FILE_URL_DTO = "http://192.168.0.202:80";
  // 测试Version
  public static final String VERSION_DTO = "V1.0";
  // 测试Level1
  public static final Integer LEVEL1_DTO = 1;
  // 测试Level2
  public static final Integer LEVEL2_DTO = 2;
  // 测试Address
  public static final String ADDRESS_DTO = "guangzhou";
  // 测试description
  public static final String DESCRIPTION_DTO = "description";
  // 测试latitude
  public static final Float LATITUDE = 100.1f;
  // 测试longitude
  public static final Float LONGITUDE = 100.1f;
  // 测试taskid
  public static final String TASKID = "1";
  // 测试CredenceNo
  public static final String CREDENCENO = "34RT57TY";
  // 测试CredenceType
  public static final Integer CREDENCE_TYPE = 1;
  // 测试passType
  public static final Integer PASSTYPE = 1;
  // 测试SubSystemNum
  public static final Integer SUBSYSTEMNUM = 1;
  // 测试alarmZoneChan
  public static final Integer ALARMZONECHAN = 1;
  // 测试setupType
  public static final Integer SETUPTYPE = 1;
  // 测试absTime
  public static final String ABSTIME = "2018-02-28 09:06:09";
  // 测试setAlarmZoneChan
  public static final String SETALARMZONECHAN = "setalarmzonechan";
  // 测试setAlarmZoneName
  public static final String SETALARMZONENAME = "setalarmzonename";
  // 测试sensorType
  public static final String SENSORTYPE = "1";

  // 测试Blacklist
  public static final Integer BLACKLIST = 1;
  // 测试NTPSERVER
  public static final String NTP_SERVER = "192.168.0.239:123";
  // 测试openDuration
  public static final Integer OPEN_DURATION = 1;
  // 测试alarmTimeout
  public static final Integer ALARM_TIMEOUT = 1;
  // 测试ALARM_TIMEOUT_NEW
  public static final String ALARM_TIMEOUT_NEW = "1";
  // 测试deviceName
  public static final String DEVICE_NAME = "deviceName";
  // 测试deviceEntryType
  public static final String DEVICE_ENTRY_TYPE = "1";
  // 测试PASSWORD
  public static final String PASS_WORD = "1";
  // 测试VirtualFlag
  public static final Integer VIRTUALFLAG = 1;
  // 测试DeciceStatus
  public static final Integer DECICE_STATUS = 1;
  // 测试startTime
  public static final String STARTTIME = "2018-3-12 20:42:30";
  // 测试endTime
  public static final String ENDTIME = "2018-3-12 23:59:59";
  // 测试credenceType
  public static final Integer CREDENCETYPE = 2;
  // 测试openMode
  public static final Integer OPEN_MODE = 1;
  // 测试userIDint
  public static final Integer USER_ID_INT = 123;
  // 测试validCount
  public static final Integer VALIDCOUNT = 1;
  // 测试DeviceProvider
  public static final String DEVICE_PROVIDER = "DeviceProvider";
  // 测试DeviceDetailType
  public static final Integer DEVICE_DETAIL_TYPE = 1;
  // 测试Mac
  public static final String MAC = "90:e4:5t:99:oi";
  // 测试MacNo
  public static final Integer MACNO = 1;
  // 测试MANUFACTURER
  public static final String MANUFACTURER = "js";
  // 测试Number
  public static final Integer NUMBER = 1;
  // 测试type
  public static final Integer TYPE = 1;
  // 测试ParamCode
  public static final String PARAM_CODE = "1";
  // 测试ParamName
  public static final String PARAM_NAME = "1";
  // 测试ParamValue
  public static final String PARAM_VALUE = "1";
  // 测试Camera1RtspAddr
  public static final String CAMERA1_RTSPADDR = "rtsp:192.168.199.65/11";
  // 测试Camera2RtspAddr
  public static final String CAMERA2_RTSPADDR = "rtsp:192.168.199.65/12";
  // 测试deviceEntryTypeInt
  public static final Integer DEVICE_ENTRY_TYPE_INT = 1;
  // 测试text
  public static final String TEXT = "text";
  // 测试DeviceType
  public static final Integer DEVICE_TYPE = 1;
  // 测试ownerID
  public static final String OWNERID = "123";
  // 测试ownerType
  public static final Integer OWNERTYPE = 1;
  // 测试CarLogo
  public static final String CARLOGO = "Benz";
  // 测试carNo
  public static final String CARNO = "京123456";
  // 测试carType
  public static final String CARTYPE = "Big";
  // 测试recCarNOColor
  public static final String RECCARNOCOLOR = "red";
  // 测试recordNumber
  public static final String RECORDNUMBER = "12";
  // 测试recordTime
  public static final String RECORDTIME = "2018-02-28 09:06:10";
  // 测试placeLockNo
  public static final String PLACELOCKNO = "1";
  // 测试placeNo
  public static final String PLACENO = "1";
  // 测试setCommandType
  public static final Integer SETCOMMANDTYPE = 1;
  // 测试Sdp
  public static final String SDP = "1";
  // 测试carportCode
  public static final String CARPORT_CODE = "1";
  // 测试operateType
  public static final Integer OPERATE_TYPE = 1;
  // 测试idle
  public static final Integer IDLE = 1;
  // 测试total
  public static final Integer TOTAL = 10;
  // 测试registerType
  public static final Integer REGISTERTYPE = 1;
  // 测试port
  public static final Integer PORT = 8080;
  // 测试size
  public static final String SIZE = "1";
  // 测试Model
  public static final String MODEL = "model";
  // 测试templet
  public static final String TEMPLET = "笑脸";
  // 测试userName
  public static final String USERNAME = "张三";
  // 测试userNo
  public static final String USERNO = "1002";
  // 测试openTime
  public static final Integer OPENTIME = 12345678;
  // 测试opTime
  public static final String OPTIME = "2018-3-12 20:40:19";
  // 测试credenceEffectTimes
  public static final Integer CREDENCEEFFECTTIMES = 1;
  // 测试fileID
  public static final String FILEID = "group1/M00/0E/C1/wKgA9lp5I0WAYXKZAAACniXEIh0513.txt";
  // 测试uuid1
  public static final String UUID_ONE = "df93c4f222b2409288eb71092c3cf275";
  // 测试uuid2
  public static final String UUID_TWO = "bd68df9ce0c04a8cba20b306546686fa";
  // 测试sourceFloor
  public static final Integer SOURCEFLOOR = 1;
  // 测试direction
  public static final Integer DIRECTION = 1;
  // 测试窗口id
  public static final Integer WINDOWS_ID = 1;
  // 测试x坐标
  public static final Integer POSITIONX = 1920;
  // 测试y坐标
  public static final Integer POSITIONY = 1920;
  // 测试高度
  public static final Integer HEIGHT = 1920;
  // 测试宽度
  public static final Integer WIDTH = 1920;
  // 测试素材标号
  public static final Integer MATERIALNO = 207;
  // 测试素材类型
  public static final String MATERIALTYPE = "picture";
  // 测试素材类型
  public static final String CHILDWORDS = "aa.jpg";
  // 测试字体大小
  public static final Integer FONTSIZE = 60;
  // 测试颜色
  public static final Integer COLOR = 123;
  //
  public static final Integer BACKCOLOR = 123;
  // 测试透明度
  public static final Integer BACKTRANSPARENT = 1;
  // 测试滚动方向
  public static final String SCROLLDIRECTION = "left";
  // 测试滚动速度
  public static final Integer SCROLSPEED = 4;
  // 测试播放时间
  public static final Integer PAGETIME = 120;
  // 测试背景图片id
  public static final Integer BACKGROUNDPIDID = 1;
  // 测试背景颜色
  public static final Integer BACKGROUNDCOLOR = 111;
  // 测试背景颜色
  public static final String PAGNAME = "测试页面";
  // 测试播放时间模式
  public static final String PLAYTIMEMOD = "selfDefine2";



  // ********************** 单元测试DTO常量end *******************************/

  // ********************** 信息发布常量end *******************************/
  // 时长类型默认值
  public static final String INFOSCREEN_DURATIONTYPE_DEFAULTVALUE = "selfDefine";
  // 素材类型默认值
  public static final String INFOSCREEN_WINMATERIATYPE_DEFAULTVALUE = "static";
  // 页面名称默认值
  public static final String INFOSCREEN_PAGNAME_DEFAULTVALUE = "page_1";
  // 审核状态默认值
  public static final String INFOSCREEN_APPROVESTATE_DEFAULTVALUE = "approved";
  // 页面id默认值
  public static final Integer INFOSCREEN_PAG_ID_DEFAULTVALUE = 1;

  public static final Integer INFOSCREEN_BACKGROUNDPIC_DEFAULTVALUE = 1;
  // ***************************信息屏参数默认值end*************************************/
  // 网关应答总线,payload中的应答结果
  public static final String GATEWAY_BACK_RESULT = "result";
  // 网关应答总线,payload中的设备生成的id
  public static final String GATEWAY_BACK_ID = "id";
  // 设备返回结果
  public static final String DEVICE_BACK_RESULT = "Result";
  // ********************** 信息发布常量end *******************************/
  // 智慧充电设备 操作结果： 失败
  public static final int RESULT_FAILED = 1;
  // 智慧充电设备 操作结果： 成功
  public static final int RESULT_SUCCESSFUL = 0;
  
  //模拟设备的基本信息
  
  public static final String AESKEY_VALUE = "2222222222222222";
  public static final String SIMULATOR_DEVICE_ID = "1008202590f05207d8f3";
  
//  public static final String SIMULATOR_IP = "192.168.126.130"; //VM
  public static final String SIMULATOR_IP = "172.25.84.19";
  public static final String SIMULATOR_MAC = "8C:EC:4B:41:9D:B3";
  public static final String CONNECT_ADDR = "H8CEC4B419DB3";
//  public static final String CONNECT_ADDR = "H599999991000";  //TEST  48EDAA372547   599999991000
  public static final int SIMULATOR_PORT = 28990;
  public static final int SIMULATO_METER_PORT = 28989;
  
  public static final String LOCAL_IP = "172.25.90.14";  //本地环境
  public static final int LOCAL_PORT = 28085;
//  public static final String GATEWAY_IP = "172.25.90.14";  //本地环境
//  public static final String GATEWAY_IP = "119.23.185.48";    //开发环境
//  public static final String GATEWAY_IP = "120.79.229.158";    //测试环境
  public static final String GATEWAY_IP = "120.77.154.63";    //UAT环境
  public static final int CHARGE_GATEWAY_PORT = 20011;  //充电网关
  public static final int CHARGE_METER_PORT = 20012;  //充电电表
//  public static final int CHARGE_METER_PORT = 2012;  //充电电表
  
  public static final int GATEWAY_PORT = 20001;  //轻量级网关
  public static final int RATEDPOWER = 3000;     //额定功率
  public static final int MAX_POWER = 7000;      //最大功率
  public static final int MIN_POWER = 2000;      //最小功率
  public static final int CURRENT_6A = 6;        //6安培电流
  
  public static final int UDP_PORT = 18090;
  
  public static final byte LORA_REGISTER = (byte)0x01;
  
}
