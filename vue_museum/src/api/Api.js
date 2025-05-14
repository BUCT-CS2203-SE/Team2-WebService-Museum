/**
 * 这个文件存放全部的后端地址
 */
export default{
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
        /**获取文物信息 OK**/
        getWenWu: '/getWenWu',
        /**获取当前用户信息 OK**/
        getUserInfo: '/getUserInfo',
        /**修改用户信息 OK**/
        changeUserInfo: '/changeUserInfo',
        /**以下是后端需要返回数据的路径(''之间是路径),后端负责人完成后将这里的信息标注为OK */
        relic:{
            /**get 请求参数：无，需要返回文物的所有类别如瓷器等，返回类型list */
            types:'/relic/relic_types',
            /**get 请求参数：无，需要返回文物朝代信息，如新石器时代、宋代等，返回类别list */
            times:'/relic/relic_times',
            /**get 请求参数：无，需要返回文物现所在博物馆信息，如大英博物馆，返回类别list */
            museums:'/relic/relic_museums',
            /**时间轴界面Api要求，完成后标注0K*/
            timeline:{
                /**get 请求参数:无，需要返回时间轴数据，返回数组,说明如下:
                 * id      整型数字文物id  string类型 例如：'1'
                 * imgUrl    首张文物图片地址  string类型 例如：'http://xxx.com/xxx.jpg'
                 * name      文物名称       string类型 例如：'xxx文物'
                 * dynasty   文物朝代       string类型 例如：'汉代'
                 * dateRange 文物年代范围   string类型 例如：'约1100年-1200年'
                 */
                data:'/relic/timeline',
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
             */
        }
                
     },

}
}