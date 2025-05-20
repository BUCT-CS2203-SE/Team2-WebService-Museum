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
            /**get 请求参数：无，需要返回文物的所有类别如瓷器等，返回类型list OK*/
            types: '/relic/relic_types',
            /**get 请求参数：无，需要返回文物朝代信息，如新石器时代、宋代等，返回类别list OK*/
            times: '/relic/relic_times',
            /**get 请求参数：无，需要返回文物现所在博物馆信息，如大英博物馆，返回类别list OK*/
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
             OK*/
            search: '/relic/relic_search',

            /**post 请求参数：{id 文物id, username 用户名}
             * @return 返回数据结构
             * @type map
             * name         文物名称
             * images[]     图片列表
             * credit       题词信息
             * era:         文物时间
             * type:        文物类别
             * material:    材料
             * atrist       作家
             * scale:       文物规格
             * description  文字描述
             * favorited    是否收藏
             * related[]    相关文物信息列表，数组元素字段如下：
             * {
             *    name  文物名称
             *    id    文物ID
             *  }
              OK*/
            detail: '/AuthRelic/relic_detail',

            /**post 请求参数：{id 文物id, username 用户名, fav bool值表示是否收藏}
             * @return ans true/false
            OK*/

            isFav: '/AuthRelic/relic_isfav',
            /**POST
             * @send map
             * username 用户名
             * rid 文物id
             * @return ans true/false
             * 
             */
            rehistory: '/AuthRelic/rehistory',


            /**时间轴界面Api要求，完成后标注0K*/
            timeline:{
                /**get 请求参数:无，需要返回时间轴数据，返回数组,说明如下:
                 * id      整型数字文物id  string类型 例如：'1'
                 * imgUrl    首张文物图片地址  string类型 例如：'http://xxx.com/xxx.jpg'
                 * title      文物名称       string类型 例如：'xxx文物'
                 * dynasty   文物朝代       string类型 例如：'汉代'
                 * dateRange 文物年代范围   string类型 例如：'约1100年-1200年'
                 */
                data: '/relic/timeline',

                /**Post 请求参数{sta: 开始年份, end: 结束年份} 
                 * @returns 返回数据结构：list[ map ]
                 * map字段如下:{
                 *  id      整型数字文物id  string类型 例如：'1'
                 *  src     文物图片地址  string类型 例如：'http://xxx.com/xxx.jpg'
                 *  title   文物名称
                 }
                */
                info:'/relic/timelineinfo'
            }
        },

        user:{
            sendInfo:"/user/sendInfo",
            /**post 请求参数：用户名，更新除了用户名以外的信息
             * username  用户名
             * password  密码      string类型
             * email     邮箱      string类型
             */
            upgradeAvatar:"/user/upgradeAvatar",
            /**post 请求参数：用户名，更新用户头像
             * avatar    头像图片地址  string类型 例如：'http://xxx.com/xxx.jpg'
                */

            /**POST 
             * @send map字段
             * username     用户名
             * relicname    文物名
             * time         截止时间
             * @return list[map]
             * map{id: 文物id, name: 文物名, time：收藏时间}
             OK*/
            getMyFav: "/user/myfav",

            /**POST
             * @send map字段
             * username     用户名
             * rid          文物id
             * @return ans true/false
             OK*/
            delMyFav: "/user/disfav",

            /**POST
             * @send map字段
             * username     用户名
             * relicname    文物名称
             * content      评论内容
             * time         截止时间
             * @return list[map]
             * map{id: 评论id，rid：文物id， name：文物名称，content：评论内容，time：评论时间}
             */
            getMyComts: "/user/mycomts",
            /**POST
             * @send map
             * id  评论id
             * @return ans true/false
             */
            delMyComts: "/user/discomts",
            /**POST
             * @send map
             * username     用户名
             * relicname    文物名
             * time         截止时间
             * @return list[map]
             * {id: 文物id, name: 文物名, time: 浏览时间}
             */
            getMyBrowser: "/user/browser",
            /**POST
             * @send
             * username  用户名
             * rid       文物di
             * @retrun ans true/false
             */
            delMyBrowser: "/user/delbrowser"
        },comments:{
            /**post 请求参数：{id   文物id}
             * @return 返回参数
             * @type map
             * name     文物名
             * comments[] 评论信息数组
             *      数组元素：{author '用户名',avatar  '头像url',content '评论详情',datetime  '评论时间'}
             OK*/
            comments: '/comments/search',
            
            /**POST 添加评论
             * @send 请求map参数{
             * username     用户名
             * id           文物ID
             * contents     评论详情
             * datetime         发表时间
             * }
             * @return ans true/false
             OK*/
            add_comments: '/comments/add',
        }
    }
}