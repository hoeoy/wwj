    function indexNavigate(url) {
        $("#content").load(url);
    }
    function goTo(url){
    	window.location.href=url;
    }

    function getUrl(){
        return "";//http://192.168.0.137:8080
    }
    function  listToTree(data){
        var result = [];
        $.each(data, function (n, e) {
            var cc = [];
            e.children_data != null && ( (cc = listToTree(e.children_data) ) && (e.nodes = cc) );
            result.push(convertItem(e));
        });
        return result;
    }

    function convertItem(o){
        o.id = o.treeId;o.text = o.treeText;  o.name = o.treeNodeName;return o;
    }

    //POS机树
    function  listToPosTree(data){
        var result = [];
        $.each(data, function (n, e) {
            var cc = [];
            e.children_data != null && ( (cc = listToPosTree(e.children_data) ) && (e.nodes = cc) );
            result.push(convertPosItem(e));
        });
        return result;
    }
    var loadTree = {
    posTreeRoot:'',
    posList:'',
    posTree:[],
    getPosTreeModule:function(){
        $.ajax({
            type: 'get',
            data: {},
            dataType: "json",
            url:getUrl() + '/Api/tenantPos/queryMerchant',
            contentType: "application/json;charset=UTF-8",
            success: function (data) {
                console.log('212 ',data.data);
                data.success && (loadTree.posTreeRoot = data.data);
                sessionStorage.posTree = JSON.stringify(listToPosTree(loadTree.posTreeRoot));
            }
        });
        $.ajax({
            type: 'get',
            data: {},
            dataType: "json",
            url:getUrl() + '/Api/tenantPos/queryDevice',
            contentType: "application/json;charset=UTF-8",
            success: function (data) {
                console.log('223 ',data.data);
                data.success && (loadTree.posList = data.data);
            }
        });
    },
    readyPackagePosTree:function(){
        //.log('准备打包')
        var _this = this;
        setTimeout(function(){
            if ( (_this.posList != '' && _this.posList != null && _this.posList.length > 0 ) && (_this.posTreeRoot != '' && _this.posTreeRoot != null && _this.posTreeRoot.length > 0 ) ){
                _this.packagePosTree();return;
            }else _this.readyPackagePosTree();
        },2000)
    },
    packagePosTree:function(){//获取 部门
        //console.log('打包')
        this.posTree = [];
        for (var i = 0;i<this.posTreeRoot.length;i++){
            this.posTree.push(this.posTreeRoot[i]);
            this.posTree[i].children_data = [];
            for (var j = 0;j<this.posList.length;j++){
                this.posList[j].pk_merchant == this.posTreeRoot[i].pk_merchant && this.posTree[i].children_data.push(this.posList[j]) ;
            }
        }
        sessionStorage.posTreeNodes = JSON.stringify(listToPosTree(loadTree.posTree));
    },

    getDeptTree:function(){
        $.ajax({
            type: 'get',
            data: {},
            dataType: "json",
            url:getUrl()+ '/Api/dept/retrieveTree',
            contentType: "application/json;charset=UTF-8",
            success: function (data) {
                if (data.success) {
                    console.log( 'charge '+data.data);
                    sessionStorage.deptTree = JSON.stringify(listToTree(data.data));
                    //console.log( 'charge nodes '+nodes)
                } else {
                    console("初始化部门树失败!:" + data.msg);
                }

            },
            error: function (data) {
                console("初始化部门树失败!");
                //	onError();
            }
        });
    },


}
    function convertPosItem(o){

        (o.merchant_code != null && o.merchant_code != undefined && o.merchant_code != '') ?  ( o.id = o.merchant_code) : ( o.id = o.device_code);
        (o.merchant_name != null && o.merchant_name != undefined && o.merchant_name != '') ?  ( o.text = o.merchant_name) : ( o.text = o.device_name);
        (o.merchant_name != null && o.merchant_name != undefined && o.merchant_name != '') ?  ( o.treeNodeName = o.merchant_name) : ( o.treeNodeName = o.device_name);
        return o;
    }
    var _id = 0;
    //报表高度 报表部门树高度
    var contentHeight  = window.innerHeight - 80  +'px';
    var deptTreeHeight  = window.innerHeight - 80 - 15 - 2 +'px';
    var reportMinHeight = window.innerHeight - 80 - 15  - 177 - 36 - 22 - 10+'px';
    var reportMaxHeight = window.innerHeight - 80 - 15  - 177 - 36 - 22 + 400 +'px';

    var posReportMinHeight = window.innerHeight - 80 - 15  - 128 - 36 - 22 - 10 +'px';
    var posReportMaxHeight = window.innerHeight - 80 - 15  - 128 - 36 - 22 + 400 +'px';

    Date.prototype.Format = function(fmt){ //author: meizz
        var o = {
            "M+" : this.getMonth()+1,                 //月份
            "d+" : this.getDate(),                    //日
            "h+" : this.getHours(),                   //小时
            "m+" : this.getMinutes(),                 //分
            "s+" : this.getSeconds(),                 //秒
            "q+" : Math.floor((this.getMonth()+3)/3), //季度
            "S"  : this.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt))
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        for(var k in o)
            if(new RegExp("("+ k +")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        return fmt;
    }