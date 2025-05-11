/**
 * 这个文件存放全部的后端地址
 */

export default{
    url: {
        /**后端已完成路由 */
        /**登录 */
        login: '/login',
        /**退出登录 */
        logout: '/logout',
        /**注册 */
        regest: '/regest',
        /**忘记密码 */
        forget: '/forget',
        /**获取邮箱验证码 */
        getCode: '/getCode',


        /**以下是后端需要返回数据的路径(''之间是路径),后端负责人完成后将这里的信息移到上方 */
        relic:{
            /**需要返回文物的所有类别如瓷器等，返回类型list */
            types:'/relic/relic_types',
            /**需要返回文物朝代信息，如新石器时代、宋代等，返回类别list */
            times:'/relic/relic_times',
            /**需要返回文物现所在博物馆信息，如大英博物馆，返回类别list */
            museums:'/relic/relic_museums',
        }
        
    }
}