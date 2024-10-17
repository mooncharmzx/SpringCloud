package com.cn.sce.api;

public class Constants {
    /**
     * 文件上传记录表 code 首字母缩写
     */
    public static final String FILE = "F";
    public static String REAL_NAME_CHECK = "实名验证卡" ;//微信实名验证
    public static String POPUP_LOG_ABBR = "REAL_NAME_CHECK" ;//系统弹窗
    public static String NO_USER_NAME = "没有使用";//

    //积分赠送dict_code
    public static final String SCENE_TYPE = "SCENE_TYPE";
    public static final String SCORE_REWARD = "SCORE_REWARD";
    public static final String DEDUCTION_RATIO = "DEDUCTION_RATIO";

    public static final String THUMBS_UP_SCORE_REWARD_COUNT = "THUMBS_UP_SCORE_REWARD_COUNT";
    public static final String READ_INFORMATION_SCORE_REWARD_COUNT = "READ_INFORMATION_SCORE_REWARD_COUNT";


    public static final String CHECK_IN_CYCLE = "CHECK_IN_CYCLE";

    public static final String CHECK_IN_15 = "CHECK_IN_15";

    public static final String CHECK_IN_30 = "CHECK_IN_30";

    public static final String SCORE_DEDUCTION_RATIO = "SCORE_DEDUCTION_RATIO";


    public static String PREFIX_CARD_NO  = "CN";

    public static String PREFIX_TICKET_NO  = "TN";


    public static String INTERFACE_LOG = "interface_log";//MonogoDb 中的日志记录表

    public static String TIME_FORMAT_Y_M_D_H_M_S = "yyyy-MM-dd-HH:mm:ss";//时间戳的格式


    public static String MONGODB_ADD_FAILURE_PARAM = "日志保存到 MongoDB 失败";//日志保存到 MongoDB 失败
    public static String MONGODB_UPDATE_FAILURE_PARAM ="日志更新到 MongoDB 失败";//日志更新到 MongoDB 失败
    public static String MONGODB_QUERY_FAILURE_PARAM = "在 MongoDB 查询日志记录失败";//在 MongoDB 查询日志记录失败
    public static String COMMA = ",";//逗号
    public static String FUNCTION_NAME = "方法";//方法名称
    public static String CHECK_MONGODB_CONFIG = "请检查 MongoDB 及配置是否正常";//请检查 MongoDB 及配置是否正常

    public static String INTERFACE_NAME_INVITATION_REAL_NAME_VERIFICATION = "Pd1 获取卡类型接口";// 邀请实名校验接口

    public static String AI_CHRONIC_DISEASE_REGISTRATION = "AI 慢病注册返回" ;//AI 慢病注册返回

    public static String  LEXIANG_SUB_CARD_FEEDBAC = "乐享副卡回传校验";// 乐享副卡回传校验

    public static String  SUBMIT_ANSWER_RESULTS = "提交答题结果" ;//提交答题结果

    public static String   SPECIFIC_QUESTIONS_AND_OPTIONS = "获取量表对应具体题目及选项" ;//获取量表对应具体题目及选项

    public static String   UPDATE_NUANHE_TOKEN = "更新暖和 Token";//更新暖和 Token

    public static String LEXIANG_RETURNS_DATA = "乐享回传数据";//乐享回传数据

    public static String CPMS_AUTO_REGISTER = "CPMS 系统自动注册" ;//CPMS 系统自动登录

    public static String CPMS_AUTO_LOGIN = "CPMS 系统自动登录" ;//CPMS 系统自动登录

    //?????修改卡类型
    public static String TAIPING_YI_KANG_YANG_CHANNEL = "tptz" ;//渠道值

    public static String CALL_OK_TREE = "调用橡树接口";//请检查 MongoDB 及配置是否正常
    public static String REDIS_KEY_MEDICAL = "customerserver::medicalservice::param::" ;
     /**
      * msg
     */
    public static final String MSG = "msg";

    /**
     * data
     */
    public static final String DATA = "data";

    /**
     * success
     */
    public static final String SUCCESS = "success";


    /**
     * error
     */
    public static final String ERROR = "error";

    /**
     * 操作失败
     */
    public static final String OPER_FAILD = "操作失败";

    /**
     * 操作成功
     */
    public static final String OPER_SUCCESS = "操作成功";

    /**
     * 新增成功
     */
    public static final String ADD_SUCCESS = "新增成功";

    /**
     * 上传成功
     */
    public static final String UPLOAD_SUCCESS = "上传成功";

    /**
     * 更新成功
     */
    public static final String UPDATE_SUCCESS = "修改成功";

    /**
     * 删除成功
     */
    public static final String DELETE_SUCCESS = "删除成功";

    /**
     * 操批量作失败
     */
    public static final String BATCH_OPER_FAILD = "批量操作失败";

    /**
     * 操作成功
     */
    public static final String BATCH_OPER_SUCCESS = "批量操作成功";

    /**
     * 批量新增成功
     */
    public static final String BATCH_ADD_SUCCESS = "批量新增成功";

    /**
     * 上传成功
     */
    public static final String BATCH_UPLOAD_SUCCESS = "批量上传成功";

    /**
     * 批量更新成功
     */
    public static final String BATCH_UPDATE_SUCCESS = "批量修改成功";

    /**
     * 批量删除成功
     */
    public static final String BATCH_DELETE_SUCCESS = "批量删除成功";

    /**
     * 新增失败
     */
    public static final String ADD_FAILD = "新增失败";

    /**
     * 新增失败
     */
    public static final String BATCH_ADD_FAILD = "批量新增失败";

    /**
     * 上传失败
     */
    public static final String BATCH_UPLOAD_FAILD = "批量上传失败";

    /**
     * 上传失败
     */
    public static final String UPLOAD_FAILD = "上传失败";

    /**
     * 更新失败
     */
    public static final String UPDATE_FAILD = "修改失败";

    /**
     * 更新失败
     */
    public static final String BATCH_UPDATE_FAILD = "批量修改失败";

    /**
     * 删除失败
     */
    public static final String DELETE_FAILD = "删除失败";

    /**
     * 删除失败
     */
    public static final String BATCH_DELETE_FAILD = "批量删除失败";

    /**
     * 逻辑删除成功
     */
    public static final String LOGIC_DELETE_SUCCESS = "逻辑删除成功";

    /**
     * 逻辑删除失败
     */
    public static final String LOGIC_DELETE_FAILD = "逻辑删除失败";

    /**
     * 批量逻辑删除成功
     */
    public static final String BATCH_LOGIC_DELETE_SUCCESS = "批量逻辑删除成功";

    /**
     * 逻辑删除失败
     */
    public static final String BATCH_LOGIC_DELETE_FAILD = "批量逻辑删除失败";

    /**
     * 保存或修改成功
     */
    public static final String SAVE_OR_UPDATE_SUCCESS = "保存或修改成功";

    /**
     * 保存或修改失败
     */
    public static final String SAVE_OR_UPDATE_FAILD = "保存或修改失败";

    /**
     * 查询成功
     */
    public static final String QUERY_SUCCESS = "查询成功";

    /**
     * 查询失败
     */
    public static final String QUERY_FAILD = "查询失败";

    /**
     * 查询失败
     */
    public static final String NO_REMINDER = "不允许催单";

    /**
     * this id is Empty！
     */
    public static final String ID_ISEMPTY = "this id is Empty!";

    /**
     * 手机号已被使用！
     */
    public static final String PHONE_EXIST = "手机号已被使用！";

    /**
     * 身份证号已被使用！
     */
    public static final String IDNO_EXIST = "身份证号已被使用！";

    /**
     * 无此卡号，请确认卡号正确性
     */
    public static final String NO_CARD = "无此卡号，请确认卡号正确性！";

    /**
     * 该手机号未注册！
     */
    public static final String PHONE_NOTEXIST = "该手机号未注册！";

    /**
     * 验证码超时！
     */
    public static final String CODE_FAILD = "验证码错误！";

    /**
     * 验证码超时！
     */
    public static final String CODE_OVER = "验证码超时！";

    /**
     * 请勿重复发送验证码！
     */
    public static final String CODE_REPEAT = "请勿重复发送验证码！";

    /**
     * 请勿重复发送验证码！
     */
    public static final String REPEAT_COMMIT = "请勿重复提交！";

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * http 请求
     */
    public static final String HTTP = "http://";

    /**
     * https 请求
     */
    public static final String HTTPS = "https://";

    /**
     * 成功标记
     */
    public static final Integer SUCCESS_INT = 0;

    /**
     * 失败标记
     */
    public static final Integer FAIL = 500;

    /**
     * 主键基础标记
     */
    public static final Long BASE_KEY = 0L;


    /**
     * 0 数量
     */
    public static final int ZERO_COUNT = 0;

    /**
     * 未删除 0
     */
    public static final String DELETE_ZERO = "0";

    /**
     * 已删除 1
     */
    public static final String DELETE_ONE = "1";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 注册
     */
    public static final String REGISTER = "Register";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String IS_ASC = "isAsc";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";

    /**
     * 请稍后再试！
     */
    public static final String LATER_OPERATION = "请稍后再试！";

    /**
     * 页码导航连续显示的页数
     */
    public static final String PAGE_NAVIGATION_TIPS = "页码导航连续显示的页数";

    /**
     * 分页大小
     */
    public static final String PAGE_SIZE_TIPS = "分页大小";

    /**
     * 当前页码
     */
    public static final String CURRENT_PAGE_TIPS = "当前页码";

    /**
     * 排序字段
     */
    public static final String ORDER_BY_TIPS = "排序字段";

    /**
     * 表 Id
     */
    public static final String TABLE_ID_TIPS = "表 Id";

    /**
     * 表 Id 数组
     */
    public static final String TABLE_ID_ARRAY_TIPS = "表 Id 数组";

    /**
     * map 参数不能为空
     */
    public static final String MAP_PARAM_NOT_NULL_TIPS = "map 参数不能为空";

    /**
     * Map 参数
     */
    public static final String MAP_PARAM = "Map 参数";

    /**
     * pageNum
     */
    public static final String P_PAGE_NUM = "pageNum";

    /**
     * pageSize
     */
    public static final String P_PAGE_SIZE = "pageSize";

    /**
     * navigatePages
     */
    public static final String P_NAVIGATION_PAGES = "navigatePages";

    /**
     * orderBy
     */
    public static final String P_ORDER_BY = "orderBy";

    /**
     * id
     */
    public static final String P_ID = "id";

    /**
     * ids
     */
    public static final String P_IDS = "ids 说明";

    /**
     * paramMap
     */
    public static final String P_MAP = "paramMap 说明";

    /**
     * 数据类型：java.lang.Integer
     */
    public static final String DATA_TYPE_INTEGER = "java.lang.Integer";

    /**
     * 数据类型：java.lang.String
     */
    public static final String DATA_TYPE_STRING = "java.lang.String";

    /**
     * 参数类型：java.lang.String
     */
    public static final String DATA_PRAM_STRING = "string";

    /**
     * 数据类型：java.lang.Long
     */
    public static final String DATA_TYPE_LONG = "java.lang.Long";

    /**
     * 数据类型：java.lang.String[]
     */
    public static final String DATA_TYPE_STRING_ARRAY = "java.lang.String[]";

    /**
     * 数据类型：java.lang.String[]
     */
    public static final String DATA_PRAM_STRING_ARRAY = "array";

    /**
     * 数据类型：java.lang.Long[]
     */
    public static final String DATA_TYPE_LONG_ARRAY = "java.lang.Long[]";

    /**
     * 数据类型：java.lang.Map
     */
    public static final String DATA_TYPE_MAP = "java.util.Map<java.lang.Object>";

    /**
     * 数据类型：java.lang.Map
     */
    public static final String TOKEN_STRING = "token";

    /**
     * 请求参数类型：header
     */
    public static final String PARAM_TYPE_HEADER = "header";

    /**
     * 请求参数类型：jRequestParam
     */
    public static final String PARAM_TYPE_REQUEST_PARAM = "RequestParam";

    /**
     * 请求参数类型：RequestBody
     */
    public static final String PARAM_TYPE_REQUEST_BODY = "RequestBody";

    /**
     * 数据类型：java.lang.Map
     */
    public static final String TOKEN_DESC = "header 中的 token";

    /**
     * 默认用户
     */
    public static final String DEFAULT_USER = "admin";

    /**
     * 创建时间字符串
     */
    public static final String CREATE_TIME_STR = "create_time";

    /**
     * 创建时间字符串
     */
    public static final String UPDATE_TIME_STR = "update_time";

    /**
     * 中台绿通服务催促接口
     */
    public static final String CENTER_APPOINT_REMINDER = "lvtong_service_reminder";

/////////////////////////////////////表 ID 头部//////////////////////////////////////////////////////////////////////

    /**
     * 人员表 ID 生成器首字母缩写
     */
    public static final String PERSON_ABBREVIATION = "CHV";


    /**
     * 代理人 ID 生成器首字母缩写
     */
    public static final String AGENT_ABBREVIATION = "AGV";

    /**
     * 评价表 ID 生成器首字母缩写
     */
    public static final String WORK_ORDER_APOINT_VALUATE = "WOAV";

    /**
     * 催单 ID 生成器首字母缩写
     */
    public static final String WORK_ORDER_APOINT_REMINDER = "WOAR";

    /**
     * 就医绿通主卡人和病人映射表 ID 生成器首字母缩写
     */
    public static final String PERSON_PATIENT_MAPPING = "PPM";

    /**
     * 卡 - 权益映射表 ID 生成器首字母缩写
     */
    public static final String CARD_RIGHTS_MAPPING = "CRM";

    /**
     * 北上广成权益 code 首字母缩写
     */
    public static final String BEI_SHANG_GUANG_CHENG= "BSGC";

    /**
     * 其他权益 code 首字母缩写
     */
    public static final String OTHER_RIGHTS= "BSGC";

    /**
     * 就医绿通工单详情表 code 首字母缩写
     */
    public static final String WORK_ORDER_APOINT = "GP";
    /**
     * 就医绿通工单详情表 code 首字母缩写
     */
    public static final String WORK_ORDER_APOINT365 = "365GP";
    /**
     * 续期 ID 生成器首字母缩写
     */
    public static final String EXTEND_RECORD_ABBR =  "ER";


    /**
     * 就医绿通工单详情表 code 首字母缩写
     */
    public static final String CUSTOMER_SERVICE_CARD_UNBIND = "CSCU";
    /******************************JWT*************************/
    public static String CLAIMS_USER_ID = "userId";  // 用户ID
    public static String CLAIMS_USERNAME = "username"; // 用户名
    public static String CLAIMS_REAL_NAME = "realName"; // 真实姓名
    public static String CLAIMS_AGENT_ID = "agentId"; // 代理人ID
    public static String CLAIMS_ROLES = "roles";
    public static String CLAIMS_OPENID = "openid";//登录人openid
    /******************************JWT*************************/

    /******************************RedisKey*************************/
    public static String REDIS_USER_DETAILS_INFO = "dsp:loginUser:";
    public static String REDIS_REFRESH_TOKEN = "dsp:token:refresh:";
    /******************************RedisKey*************************/

    /******************************Auth*************************/
    public static String AUTHORIZATION_HEADER = "Authorization";
    public static String AUTHORIZATION_REFRESH_HEADER = "X-Authorization";
    public static String AUTHORIZATION_BEARER = "Bearer ";
    public static String DEFAULT_PASSWORD = "circle";

    public static String SMS_LOGIN = "sms:login:";
    /******************************RedisKey*************************/
    /**
     * 超时时间:1小时:60*60
     */
    public static  long EXPIRE_TIME_HOURE = 3600 ;
    public static String TRACE_ID = "traceId";

    /**********************************************************************************************************/
}
