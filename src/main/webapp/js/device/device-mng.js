$(function (device) {
    document.getElementById("tree").style.maxHeight=$(window).height()+'px';
    var pk_device_time;
    var time_name;

    device.initTreeView = function () {
        $.ajax({
            type: 'get',
            dataType: "json",
            url: getUrl() + '/Api/deviceTime/retrieveTree' + '?' + Math.random(),
            contentType: "application/json;charset=UTF-8",
            success: function (data) {
                if (data.success) {
                    var nodes = JSON.stringify(listToTree(data.data));
                    $('#tree').treeview({
                        data: nodes,
                        onNodeSelected: function (event, node) {

                            var length = $('#table').bootstrapTable('getData').length;
                            /*if(length > 0){
                                $("#btn_add").attr('disabled','disabled');
                            }*/
                            $("#btn_edit").removeAttr('disabled');
                            $("#btn_delete").removeAttr('disabled');
                            pk_device_time = node.id;
                            time_name = node.text;
                            //刷新表格
                            device.refresh();

                        },
                        onNodeUnselected: function (event, node){
                            /*$("#btn_add").attr('disabled',false);*/
                        }
                    });
                } else {
                    alert("初始化树失败!:" + data.msg);
                }
            },
            error: function (data) {
                Notify('连接错误', 'top-right', '5000', 'danger', 'fa-times-circle', true);
            }
        });
    }

    device.initTable = function () {
        $('#table').bootstrapTable({
            url: getUrl() + '/Api/deviceSub/find',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: false,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            queryParams: function (params) {
                var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                    limit: params.limit,   //页面大小
                    offset: params.offset,  //页码
                    pk_device_time:pk_device_time
                };
                return temp;
            },//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            //search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            //showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            //clickToSelect: true,                //是否启用点击选中行
            height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            //uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
            showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            columns: [{
                checkbox: true
            }, {
                field: 'pk_device_time_sub',
                title: ''
            }, {
                field: 'pk_device_time',
                title: ''
            }, {
                field: 'sub_name',
                title: '名称'
            }, {
                field: 'start_time',
                title: '开始时间'
            }, {
                field: 'end_time',
                title: '结束时间'
            }, {
                field: 'frequency_time',
                title: '次数'
            }],
        });
        $('#table').bootstrapTable('hideColumn', 'pk_device_time_sub');
        $('#table').bootstrapTable('hideColumn', 'pk_device_time');
    }

    device.initButton = function () {

        $("#btn_add").click(function () {
            $("#addTimeModal").modal('show');

            $("#time_name").val("");
            $("input[name='sub_name']").val("");
            $("input[name='start_time']").val("");
            $("input[name='end_time']").val("");
            $("input[name='frequency_time']").val("");
            $("input[name='pk_device_time_sub']").val("");
            pk_device_time = "";

        });

        //删除
        $("#btn_delete").click(function(){
            $.ajax({
                type: 'get',
                dataType: "json",
                data:{
                    pk_device_time:pk_device_time
                },
                url: getUrl() + '/Api/deviceTime/delTime',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.success) {
                        Notify('删除成功', 'top-right', '5000', 'primary', 'fa-times-circle', true);
                        device.initTreeView();
                    } else {
                        Notify('删除失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                    }
                },
                error: function (data) {
                    Notify('连接错误', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                }
            });
        });

        //编辑
        $("#btn_edit").click(function () {

            $("#time_name").val("");
            $("input[name='sub_name']").val("");
            $("input[name='start_time']").val("");
            $("input[name='end_time']").val("");
            $("input[name='frequency_time']").val("");
            $("input[name='pk_device_time_sub']").val("");

            var datas = $("#table").bootstrapTable("getData");

            $("#time_name").val(time_name);

            $.each(datas,function(idx,obj){
                $("#data"+idx + " input[name='sub_name']").val(obj.sub_name);
                $("#data"+idx + " input[name='start_time']").val(obj.start_time);
                $("#data"+idx + " input[name='end_time']").val(obj.end_time);
                $("#data"+idx + " input[name='frequency_time']").val(obj.frequency_time);
                $("#data"+idx + " input[name='pk_device_time_sub']").val(obj.pk_device_time_sub);
            });

            $("#addTimeModal").modal('show');

        });

        //保存
        $("#small_sure").click(function () {
            var result = vm.computingTime();
            console.log(result);
            if(result == false){
                return;
            }else{
                var childrens = $("#children_data").children();

                var sub_name;
                var start_time;
                var end_time;
                var frequency_time;
                var pk_device_time_sub;
                var time_name = $("#time_name").val();

                if(time_name == null || time_name == ""){
                    Notify('请填写完整信息', 'top-right', '5000', 'default', 'fa-times-circle', true);
                    return;
                }

                var subs = [];

                $.each(childrens,function(idx,obj){

                    sub_name = $("#data"+idx + " input[name='sub_name']").val();
                    start_time = $("#data"+idx + " input[name='start_time']").val();
                    end_time = $("#data"+idx + " input[name='end_time']").val();
                    frequency_time = $("#data"+idx + " input[name='frequency_time']").val();
                    pk_device_time_sub = $("#data"+idx + " input[name='pk_device_time_sub']").val();

                    reg = /[\d][\d]:[\d][\d]:[\d][\d]/;

                    if(!reg.test(start_time)){
                        start_time = start_time + ":00";
                    }
                    if(!reg.test(end_time)){
                        end_time = end_time + ":00";
                    }

                    //要么全填,要么全不填
                    var flag = sub_name != "" && start_time != "" && end_time != "" && frequency_time != "";

                    if(flag){
                        subs.push({
                            sub_name:sub_name,
                            start_time:start_time,
                            end_time:end_time,
                            frequency_time:frequency_time,
                            pk_device_time_sub:pk_device_time_sub,
                            pk_device_time:pk_device_time
                        });
                    }

                });

                $.ajax({
                    type: 'post',
                    dataType: "json",
                    data:JSON.stringify({
                        pk_device_time:pk_device_time,
                        time_name:time_name,
                        subs:subs
                    }),
                    url: getUrl() + '/Api/deviceTime/saveTime'+ '?' + Math.random(),
                    contentType: "application/json;charset=UTF-8",
                    success: function (data) {
                        if (data.success) {
                            Notify('保存成功', 'top-right', '5000', 'primary', 'fa-times-circle', true);
                            device.refresh();device.initTreeView();
                        } else {
                            Notify('保存失败'+ data.message, 'top-right', '5000', 'danger', 'fa-times-circle', true);
                        }
                    },
                    error: function (data) {
                        Notify('连接错误', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                    }
                });
                $("#addTimeModal").modal('hide');

            }

        });

    };
    var id=1;
    device.refresh = function refresh() {
        id=id+1;
        var opt = {
            url: getUrl() + "/Api/deviceSub/find",
            silent: true,
            query: JSON.stringify({
                limit: 10,
                offset: 0,
                type: 1,
                level: 2,
                id:id
            })
        };

        $("#table").bootstrapTable('refresh', opt);
    }

    //



    //1.初始化左侧树
    device.initTreeView();

    //2.初始化Table
    device.initTable();

    //3.初始化Button的点击事件
    device.initButton();

});
var vm = new Vue({
    el:'#children_data',
    data:{
        startTime1:'',endTime1:'',
        startTime2:'',endTime2:'',
        startTime3:'',endTime3:'',
        startTime4:'',endTime4:'',
        startTime5:'',endTime5:'',
        timeQuantum1:{},timeQuantum2:{},timeQuantum3:{},timeQuantum4:{},timeQuantum5:{}
    },
    mounted:function(){
        this.$nextTick(function () { });
    },
    methods:{
        getTime:function(){
            var str1='',str2='';
            for(var i = 1;i<=5;i++){
               str1 = 'vm.startTime' + i + ' = document.querySelector("#data' + (i-1) + '_start_time").value';
               str2 = ' vm.endTime' + i + '  = document.querySelector("#data' + (i-1) + '_end_time").value';
               eval(str1);eval(str2);
            }
        },
        computingTime:function(){//var startTime1 = '00:30:00' ; startTime1.substr(0,2);
            this.timeQuantum1 = {} ;this.timeQuantum2 = {} ;this.timeQuantum3 = {} ;this.timeQuantum4 = {} ;this.timeQuantum5 = {} ;
            this.getTime();
            var str1='',str2='';
            for(var i = 1;i<=5;i++){
                if(this.contrast(i)){console.log(i)
                    str1 = 'vm.timeQuantum' + i + '.startTime = parseInt( vm.startTime' + i + '.substr(0,2)) * 60 + parseInt(vm.startTime' + i + '.substr(3,2));';
                    str2 = 'vm.timeQuantum' + i + '.endTime   = parseInt( vm.endTime' + i + '.substr(0,2)) * 60 + parseInt(vm.endTime' + i + '.substr(3,2));';
                    eval(str1);eval(str2);
                }
            }
            var result = this.key();
            return result;
        },
        key:function(){
            console.log('key');
            var result = false;
            var count = 0;
            for (var i = 1; i<= 5 ; i++){
                 this.checkout( eval( 'vm.checkout(vm.timeQuantum' + i +'.startTime) ' )) && count ++;
            }
            if(count == 1){
                var i = 1;
                if( eval( 'vm.timeQuantum' + i +'.startTime == vm.timeQuantum' + i +'.endTime ||  vm.timeQuantum' + i +'.startTime > vm.timeQuantum' + i +'.endTime') ){
                    Notify('结束时间不能小于或等于开始时间，请选择正确的时间', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                    return false ;
                }else{
                    return true
                }
            }else  {
                for (var i = 1; i<= 5 ; i++){
                    if ( eval( 'vm.checkout(vm.timeQuantum' + i +'.startTime) ' ) ){
                        if( eval( 'vm.timeQuantum' + i +'.startTime == vm.timeQuantum' + i +'.endTime ||  vm.timeQuantum' + i +'.startTime > vm.timeQuantum' + i +'.endTime') ){
                            Notify('结束时间不能小于或等于开始时间，请选择正确的时间', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                            return false ;
                        }else {
                            for  (var j = 1; j<= 5 ; j++){
                                if ( i == j  ){
                                    continue;
                                }else {
                                    if( eval( 'vm.checkout(vm.timeQuantum' + j +'.startTime)')){
                                        if( eval('vm.timeQuantum' + j +'.startTime >= vm.timeQuantum' + i +'.startTime && vm.timeQuantum' + j +'.startTime < vm.timeQuantum' + i +'.endTime') ){
                                            Notify('时间段重叠，请选择正确的时间', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                                            return false ;
                                        }else result = true;
                                    }else {
                                        result = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }


            return result;
        },
        checkout:function(item){//检测 值   有值  返回true  || 没值 返回false
            item = eval(item);
            if(item != '' && item != undefined && item != null) {
                return true;
            }else return false;
        },
        contrast:function(index){//检测 开始时间 和 结束时间   要么不填 要么两个都填    。。。。两个都填了 返回 true  || 没填 返回false || 只填了开始时间 返回false 并alert
            if(vm.checkout('vm.startTime' + index) ){
                if (vm.checkout('vm.endTime' + index))  return true;
                else {
                    alert('请选择时结束时间')
                    return false;
                }
            }else if (vm.checkout('vm.endTime' + index)){
                if (vm.checkout('vm.startTime' + index))  return true;
                else {
                    alert('请选择时开始时间')
                    return false;
                }
            }
        }
    }
});















