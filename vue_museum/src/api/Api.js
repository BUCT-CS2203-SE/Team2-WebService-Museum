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
        /**获取文物信息 **/
        getWenWu: '/getWenWu',
        /**获取当前用户信息 **/
        getUserInfo: '/getUserInfo',
        /**修改用户信息 **/
        changeUserInfo: '/changeUserInfo',
        /**以下是后端需要返回数据的路径(''之间是路径),后端负责人完成后将这里的信息移到上方 */
        relic:{
            /**需要返回文物的所有类别如瓷器等，返回类型list */
            types:'/relic/relic_types',
            /**需要返回文物朝代信息，如新石器时代、宋代等，返回类别list */
            times:'/relic/relic_times',
            /**需要返回文物现所在博物馆信息，如大英博物馆，返回类别list */
            museums:'/relic/relic_museums',
            /**时间轴界面Api要求，完成后标注0K*/
            timeline:{
                /**get 请求参数:无，需要返回时间轴数据，结构1ist[map],说明如下:
                 * *数组按文物朝代递增只取第一件文物数据，map字段解释如下
                 * id      整型数字文物id
                 * imgUrl    首张文物图片地址
                 * name      文物名称
                 * dynasty   文物朝代
                 * dateRange 文物年代
                 */
                data:'/relic/timeline',
        },
        user:{
            getInfo:"/user/getInfo",
            /** get  请求参数：用户名，返回用户数据，说明如下：
             * username  用户名
             * realname  真实姓名
             * idNumber  身份证号
             * phone     手机号
             * email     邮箱
             * avatar    头像图片地址
            */
            sendInfo:"/user/sendInfo",
            /**post 请求参数：用户名，更新除了用户名以外的信息
             * password  密码
             * realname  真实姓名
             * idNumber  身份证号
             * phone     手机号
             * email     邮箱
             */
            upgradeAvatar:"/user/upgradeAvatar",
            /**post 请求参数：用户名，更新用户头像
             * avatar    头像图片地址
             */

        }
                
     },

}
}
