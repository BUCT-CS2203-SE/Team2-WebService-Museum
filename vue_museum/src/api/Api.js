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
        }
    },
   data:{
    getWenWu:{
        0: {id: 1, imageUrl: 'https://picsum.photos/300/200?random=0', name: '夏朝 青铜器', dynasty: '夏朝', dateRange: '约1000-900年'},
        1: {id: 2, imageUrl: 'https://picsum.photos/300/200?random=1', name: '商朝 瓷器', dynasty: '商朝', dateRange: '约1100-1000年'},
        2: {id: 3, imageUrl: 'https://picsum.photos/300/200?random=2', name: '周朝 玉器', dynasty: '周朝', dateRange: '约1200-1100年'},
    },
    getUserInfo:{
        username:'test',
        realname:'张三',
        idNumber: '320123199001011234',
        phone: '13800138000',
        email: 'test@example.com',
        avatar: 'https://randomuser.me/api/portraits/men/1.jpg',//头像图片格式应为JPG或PNG  
    },
    changeUserInfo:{
        username:'test111',
        password:'123456',
        realname:'张三123',
        idNumber: '3201231999999999',
        phone: '1389990138000',
        email: 'test111@example.com',
        avatar: 'https://randomuser.me/api/portraits/men/1.jpg',//头像图片格式应为JPG或PNG
    },
   },
   method:{
    getWenWu:'get',
    getUserInfo:'get',
    changeUserInfo:'post',
   }
}
