/**
 * 这个文件存放全部的后端地址
 */
export default {
    url: {
        /**后端已完成路由 */
        /**登录 OK*/
        login: '/login',
        /**退出登录 OK*/
        logout: '/logout',
        /**注册 OK*/
        regest: '/regest',
        /**忘记密码 OK*/
        forget: '/forget',
        /**获取邮箱验证码 OK*/
        getCode: '/getCode',

        /**以下是后端需要返回数据的路径(''之间是路径),后端负责人完成后将这里的信息标注为OK */
        relic: {
            /**get 请求参数：无，需要返回文物的所有类别如瓷器等，返回类型list */
            types: '/relic/relic_types',
            /**get 请求参数：无，需要返回文物朝代信息，如新石器时代、宋代等，返回类别list */
            times: '/relic/relic_times',
            /**get 请求参数：无，需要返回文物现所在博物馆信息，如大英博物馆，返回类别list */
            museums: '/relic/relic_museums',

            /**post 请求参数以下：
             *  类型map
             *  type:null,      //文物类型
             *  time:null,      //文物时代
             *  museum: null,   //博物馆
             *  sortby:null,    //排序方式,null按ID，1按名称，2按年代
             *  name: null,     //按文物名称模糊查询
             *  artist: null,    //按作家查询
             *  pageindex: 1,   //页码号
             *  pagesize:8      //页大小
             * @returns 返回数据结构：map
             * total，  总项目数
             * data:[map],
             * 其中map字段如下:{
             *  id  文物id
             *  src 网络加载路径
             *  title 文物名
             * }        文物数据
             */
            search: '/relic/relic_search',

            /**post 请求参数：{id 文物id, username 用户名}
             * @return 返回数据结构
             * @type map
             * name         文物名称
             * images[]     图片列表
             * era:         文物时间
             * atrist       作家
             * scale:       文物规格
             * description  文字描述
             * favorited    是否收藏
             * related[]    相关文物信息列表，数组元素字段如下：
             * {
             *    name  文物名称
             *    id    文物ID
             *  }
              */
            detail: '/relic/relic_detail',

            /**post 请求参数：{id 文物id, username 用户名, fav bool值表示是否收藏}
             * @return 无
            */
            isFav: '/relic/relic_isfav',


            /**时间轴界面Api要求，完成后标注0K*/
            timeline:{
                /**get 请求参数:无，需要返回时间轴数据，返回数组,说明如下:
                 * id      整型数字文物id  string类型 例如：'1'
                 * imgUrl    首张文物图片地址  string类型 例如：'http://xxx.com/xxx.jpg'
                 * name      文物名称       string类型 例如：'xxx文物'
                 * dynasty   文物朝代       string类型 例如：'汉代'
                 * dateRange 文物年代范围   string类型 例如：'约1100年-1200年'
                 */
                data: '/relic/timeline',
            },
            user: {
                getInfo: "/user/getInfo",
                /** get  请求参数：用户名，返回用户数据，说明如下：
                 * username  用户名
                 * realname  真实姓名
                 * idNumber  身份证号
                 * phone     手机号
                 * email     邮箱
                 * avatar    头像图片地址
                */
                sendInfo: "/user/sendInfo",
                /**post 请求参数：用户名，更新除了用户名以外的信息
                 * password  密码
                 * realname  真实姓名
                 * idNumber  身份证号
                 * phone     手机号
                 * email     邮箱
                 */
                upgradeAvatar: "/user/upgradeAvatar",
                /**post 请求参数：用户名，更新用户头像
                 * avatar    头像图片地址
                 */
            }

        },

        user:{
            getInfo:"/user/getInfo",
            /** get  请求参数：用户名，返回一个包含用户数据的对象，说明如下：
             * username  用户名   string类型 例如：'test'
             * realname  真实姓名 string类型 例如：'xxx'
             * idNumber  身份证号  string类型 
             * phone     手机号   string类型
             * email     邮箱     string类型
             * avatar    头像图片地址  string类型 例如：'http://xxx.com/xxx.jpg'
            */
            sendInfo:"/user/sendInfo",
            /**post 请求参数：用户名，更新除了用户名以外的信息
             * password  密码      string类型
             * realname  真实姓名  string类型
             * idNumber  身份证号  string类型
             * phone     手机号    string类型
             * email     邮箱      string类型
             */
            upgradeAvatar:"/user/upgradeAvatar",
            /**post 请求参数：用户名，更新用户头像
             * avatar    头像图片地址  string类型 例如：'http://xxx.com/xxx.jpg'

        comments:{
            /**post 请求参数：{id   文物id}
             * @return 返回参数
             * @type map
             * name     文物名
             * comments[] 评论信息数组
             *      数组元素：{author '用户名',avatar  '头像url',content '评论详情',datetime  '评论时间'}
             */
            comments: '/comments/search',
            
            /**POST 添加评论
             * @send 请求map参数{
             * username     用户名
             * id           文物ID
             * contents     评论详情
             * time         发表时间
             * }
             * @return 无

             */
            add_comments: '/comments/add',
        }
    }
}