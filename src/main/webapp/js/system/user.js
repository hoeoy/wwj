$(function (user) {

    var pk_user;

    user.initTable = function () {
        $('#table').bootstrapTable({
            url:getUrl()+ '/Api/user/retrieve',         //请求后台的URL（*）
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
                    user_code: $("#query_code").val(),
                    user_name: $("#query_name").val(),
                    role_name: $("#query_role_name").val(),
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
            clickToSelect: true,                //是否启用点击选中行
            height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            //uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
            showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            columns: [{
                checkbox: true
            }, {
                field: 'pk_user',
                title: ''
            }, {
                field: 'pk_role',
                title: ''
            }, {
                field: 'user_code',
                title: '账号'
            }, {
                field: 'user_name',
                title: '名称'
            }, {
                field: 'password',
                title: '密码'
            },{
                field: 'role_name',
                title: '角色'
            }],
            onCheck:function(){
                var selected_btn=$('#table').bootstrapTable("getSelections");
                if(selected_btn.length ==1){
                    $("#btn_edit").removeAttr('disabled');
                    $("#btn_delete").removeAttr('disabled');
                    $("#btn_role").removeAttr('disabled');
                }else if(selected_btn.length>1){
                    $("#btn_edit").attr('disabled','disabled');
                    $("#btn_role").attr('disabled','disabled');
                    $("#btn_delete").removeAttr('disabled');
                }
            },
            onUncheck:function(){
                var selected_btn=$('#table').bootstrapTable("getSelections");
                if(selected_btn.length ==1){
                    $("#btn_edit").removeAttr('disabled');
                    $("#btn_delete").removeAttr('disabled');
                    $("#btn_role").removeAttr('disabled');
                }else if(selected_btn.length>1){
                    $("#btn_edit").attr('disabled','disabled');
                    $("#btn_role").attr('disabled','disabled');
                    $("#btn_delete").removeAttr('disabled');
                }else if(selected_btn.length==0){
                    $("#btn_edit").attr('disabled','disabled');
                    $("#btn_role").attr('disabled','disabled');
                    $("#btn_delete").attr('disabled','disabled');
                }
            },
            onCheckAll:function(){
                var selected_btn=$('#table').bootstrapTable("getSelections");
                if(selected_btn.length ==1){
                    $("#btn_edit").removeAttr('disabled');
                    $("#btn_delete").removeAttr('disabled');
                    $("#btn_role").removeAttr('disabled');
                }else if(selected_btn.length>1){
                    $("#btn_edit").attr('disabled','disabled');
                    $("#btn_role").attr('disabled','disabled');
                    $("#btn_delete").removeAttr('disabled');
                }else if(selected_btn.length==0){
                    $("#btn_edit").attr('disabled','disabled');
                    $("#btn_delete").attr('disabled','disabled');
                    $("#btn_role").attr('disabled','disabled');
                }
            },
            onUncheckAll:function(){
                $("#btn_edit").attr('disabled','disabled');
                $("#btn_delete").attr('disabled','disabled');
                $("#btn_role").attr('disabled','disabled');
            }
        });
        $('#table').bootstrapTable('hideColumn', 'pk_user');
        $('#table').bootstrapTable('hideColumn', 'pk_role');
    }

    user.initButton = function () {
        //查询
        $("#btn_query").click(function () {
            user.refresh();
        });

        //新增
        $("#btn_add").click(function(){

            $("#user_code").val("");
            $("#user_name").val("");
            $("#password").val("");
            $("#pk_device").val("");

            $("#mymodal-data").modal('show');
        });

        //修改
        $("#btn_edit").click(function(){

            var selected = $('#table').bootstrapTable("getSelections");
            pk_user = selected[0].pk_user;
            $("#user_code").val(selected[0].user_code);
            $("#user_name").val(selected[0].user_name);
            $("#password").val(selected[0].password);
            $("#pk_device").val(selected[0].pk_device);
            $("#mymodal-data").modal('show');

        });

        //删除
        $("#btn_delete").click(function(){
            var selected = $('#table').bootstrapTable("getSelections");

            var pks = [];

            $.each(selected,function(idx,obj){
                pks.push(obj.pk_user);
            });

            $.ajax({
                type: 'post',
                data:JSON.stringify(pks),
                dataType:"json",
                url:getUrl()+ '/Api/user/delete',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.success) {
                        Notify('删除成功', 'top-right', '5000', 'primary', 'fa-times-circle', true);

                        user.refresh();

                    } else {
                        Notify('删除失败,'+data.message, 'top-right', '5000', 'danger', 'fa-times-circle', true);
                    }
                },
                error: function (data) {
                    Notify('删除失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                }
            });

        });

        //分配角色
        $("#btn_role").click(function(){
            $("#roles_ul").html("");

            var selected = $('#table').bootstrapTable("getSelections");

            $.ajax({
                type: 'get',
                url: "Api/role/retrieveAll",
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                async:false,
                success: function (data) {

                    $.each(data.data,function(idx,obj){

                        if(obj.pk_role == selected[0].pk_role){
                            $("#roles_ul").append("<li class='list-group-item selected'><a href='#'><span data-pk-role='"+obj.pk_role+"'>"+obj.role_name+"</span></a></li>");
                        }else{
                            $("#roles_ul").append("<li class='list-group-item'><a href='#'><span data-pk-role='"+obj.pk_role+"'>"+obj.role_name+"</span></a></li>");
                        }
                    });
                },
                error: function (data) {
                    Notify('连接服务器错误', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                }
            });
            $("#myModal").modal('show');
        });

        $("#okBtn").click(function(){

            var selected = $('#table').bootstrapTable("getSelections");

            var pk_user = selected[0].pk_user;
            var pk_role_old = selected[0].pk_role;
            var roles = $("#roles_ul").children();
            var pk_role_new;
            $.each(roles,function(idx,obj){
                if($(obj).hasClass("selected")){
                    pk_role_new = $(obj).children().children().attr("data-pk-role");
                }
            });

            if(pk_role_old == pk_role_new){
                Notify('权限没有发生变化', 'top-right', '5000', 'info', 'fa-times-circle', true);
                $("#myModal").modal('hide')
            }else{

                console.log(JSON.stringify({
                            "pk_user":pk_user,
                            "pk_role":pk_role_new
                        }));

                $.ajax({
                    type: 'post',
                    url: "Api/user/save",
                    contentType: "application/json;charset=UTF-8",
                    dataType: "json",
                    data: JSON.stringify({
                        "pk_user":pk_user,
                        "pk_role":pk_role_new
                    }),
                    success: function (data) {
                        if(data.success == true){
                            //这里需要重新加载表格数据
                            Notify('分配角色成功', 'top-right', '5000', 'primary', 'fa-times-circle', true);
                            $("#myModal").modal('hide')
                            user.refresh();
                        }else{
                            Notify('分配角色失败,'+data.message, 'top-right', '5000', 'danger', 'fa-times-circle', true);

                        }
                    },
                    error: function (data) {
                        Notify('连接服务器失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);

                    }
                });
            }
        });

        $("#roles_ul").on('click','li',function(){

            if($(this).hasClass("selected")){
                $(this).removeClass("selected");
            }

            $(this).addClass("selected");

            $(this).siblings().removeClass("selected");
        });

        //保存
        $("#save").click(function(){

            var user_code = $("#user_code").val();
            var user_name = $("#user_name").val();
            var password = $("#address").val();
            var pk_device = $("#pk_device").val();

            if(user_code == "" || user_name == "" || password == "" ){

                Notify('请填写完整信息', 'top-right', '5000', 'info', 'fa-times-circle', true);
                return;
            }

            var sendData = {
                pk_user:pk_user,
                user_code:user_code,
                user_name:user_name,
                pk_device:pk_device,
                password:$("#password").val()
            }

            console.log(sendData);

            $.ajax({
                type: 'post',
                data:JSON.stringify(sendData),
                dataType:"json",
                url:getUrl()+ '/Api/user/save',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.success) {
                        Notify('保存成功', 'top-right', '5000', 'primary', 'fa-times-circle', true);
                        $("#mymodal-data").modal('hide');
                        user.refresh();

                    } else {
                        Notify('保存失败,'+data.message, 'top-right', '5000', 'danger', 'fa-times-circle', true);
                    }
                },
                error: function (data) {
                    Notify('保存失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                }
            });
            pk_user = null;
        });

        //重置
        $("#btn_reset").click(function(){
            $("#query_code").val("");
            $("#query_name").val("");
            $("#query_role_name").val("");
            user.refresh();
        });

        //用户自助
        $("#btn_userself").click(function () {
            $("#left_psns ul").html("");
            $("#right_psns ul").html("");
            $("#plchargemoney").val("")
            $("#self_password").val("");
            $("#roles").html("");
            $("#roles").append("<option value=''>请选择</option>");
            $.ajax({
                type: 'get',
                data: {company_code: "1"},
                dataType: "json",
                url: getUrl()+'/Api/dept/retrieveTree' + '?' + Math.random(),
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.success) {
                        var nodes = JSON.stringify(listToTree(data.data));
                        $('#left-tree').treeview({
                            data: nodes,
                            onNodeSelected: function (event, node) {
                                $.ajax({
                                    type: 'get',
                                    url: getUrl()+"/Api/user/retrievenus",
                                    data: {department_code: node.id},
                                    dataType: "json",
                                    contentType: "application/json;charset=UTF-8",
                                    success: function (data) {

                                        $("#left_psns ul").html("");
                                        $.each(data.data, function (idx, obj) {
                                            $("#left_psns ul").append("<li class='list-group-item'><a href='#'><span  data-pk-staff='" + obj.pk_staff + "' data-staff-code='"+obj.staff_code+"'>" + obj.staff_code +"&nbsp;"+ obj.staff_name+"</span></a></li>");
                                        });

                                    },
                                    error: function (data) {
                                        Notify('获取数据失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                                    }
                                });
                            }
                        });
                    } else {
                        alert("初始化部门树失败!:" + data.msg);
                    }
                },
                error: function (data) {
                    alert("初始化部门树失败!");
                }
            });

            $.ajax({
                type: 'get',
                dataType: "json",
                url: getUrl()+'/Api/role/retrieveAll',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.success) {

                        $.each(data.data,function(idx,obj){
                            $("#roles").append("<option value='"+obj.pk_role+"'>"+obj.role_name+"</option>");
                        });


                    } else {
                    }
                },
                error: function (data) {
                }
            });

            $("#plcharge").modal('show');
        });

        //批量充值选择人员改变背景色
        $("#right_psns ul").on('click', 'li', function () {
            if ($(this).hasClass("selected")) {
                $(this).removeClass("selected");
            } else {
                $(this).addClass("selected");
            }
            //$(this).siblings().removeClass("selected");
        });

        $("#left_psns ul").on('click', 'li', function () {
            if ($(this).hasClass("selected")) {
                $(this).removeClass("selected");
            } else {
                $(this).addClass("selected");
            }
            //$(this).siblings().removeClass("selected");
        });


        $("#move_right_one").click(function () {
            var leftitems = $("#left_psns ul li");
            $.each(leftitems, function (idx, obj) {
                if ($(obj).hasClass("selected")) {
                    var right_psns = $("#right_psns ul li");
                    var flag = true;
                    $.each(right_psns, function (idx1, obj1) {
                        if ($(obj).children().children().attr("data-pk-staff") == $(obj1).children().children().attr("data-pk-staff")) {
                            flag = false;
                        }
                    });
                    if (flag) {
                        $("#right_psns ul").append("<li class='list-group-item'>" + $(obj).html() + "</li>");
                        $(obj).remove();
                        //$(obj).removeClass("selected");
                    } else {
                        if (confirm("人员已经存在，确认重复添加？")) {
                            $("#right_psns ul").append("<li class='list-group-item'>" + $(obj).html() + "</li>");
                            $(obj).removeClass("selected");
                        }
                    }
                }
            });
            //$("#right_psns ul").children().removeClass("selected");
        });

        $("#move_right_all").click(function () {
            var leftitems = $("#left_psns ul");
            var right_psns = $("#right_psns ul li");
            var flag = true;
            $.each(leftitems.children(), function (idx, obj) {
                $.each(right_psns, function (idx1, obj1) {
                    if ($(obj).children().children().attr("data-pk-staff") == $(obj1).children().children().attr("data-pk-staff")) {
                        flag = false;
                    }
                });
            });

            if (flag) {
                $("#right_psns ul").append($(leftitems).html());
                $("#right_psns ul").children().removeClass("selected");
                $("#left_psns ul").html("");
            } else {
                if (confirm("存在重复人员,确认添加？")) {
                    $("#right_psns ul").append($(leftitems).html());
                    $("#right_psns ul").children().removeClass("selected");
                }
            }

        });

        $("#move_left_one").click(function () {
            var right_psns = $("#right_psns ul li");
            $.each(right_psns, function (idx, obj) {
                if ($(obj).hasClass("selected")) {
                    var leftitems = $("#left_psns ul li");
                    var flag = true;
                    $.each(leftitems, function (idx1, obj1) {
                        if ($(obj).children().children().attr("data-pk-staff") == $(obj1).children().children().attr("data-pk-staff")) {
                            flag = false;
                        }
                    });
                    if (flag) {
                        $("#left_psns ul").append("<li class='list-group-item'>" + $(obj).html() + "</li>");
                        $(obj).remove();
                        //$(obj).removeClass("selected");
                    } else {
                        if (confirm("人员已经存在，确认重复添加？")) {
                            $("#right_psns ul").append("<li class='list-group-item'>" + $(obj).html() + "</li>");
                            $(obj).removeClass("selected");
                        }
                    }
                }
            });
           // $("#left_psns ul").children().removeClass("selected");

          /*  var rightitems = $("#right_psns ul li");
            $.each(rightitems, function (idx, obj) {
                if ($(obj).hasClass("selected")) {
                    $(obj).remove();
                }
            });*/
        });

        $("#move_left_all").click(function () {
            var leftitems = $("#left_psns ul li");
            var right_psns = $("#right_psns ul");
            var flag = true;
            $.each(right_psns.children(), function (idx, obj) {
                $.each(leftitems, function (idx1, obj1) {
                    if ($(obj).children().children().attr("data-pk-staff") == $(obj1).children().children().attr("data-pk-staff")) {
                        flag = false;
                    }
                });
            });

            if (flag) {
                $("#left_psns ul").append($(right_psns).html());
                $("#left_psns ul").children().removeClass("selected");
                $("#right_psns ul").html("");
            } else {

            }

        });

        $("#plokBtn").click(function(){
            var password = $("#self_password").val();
            var pk_role = $("#roles").val();
            if(password == ""){
                Notify('请填写默认密码', 'top-right', '5000', 'info', 'fa-times-circle', true);
                return;
            }

            var psns = $("#right_psns ul").children();
            if(psns.length == 0){
                Notify('请选择人员', 'top-right', '5000', 'info', 'fa-times-circle', true);
                return;
            }

            var params = [];

            $.each(psns,function(idx,obj){

                var param = {
                    pk_role:pk_role,
                    pk_staff:$(obj).children().children().attr("data-pk-staff"),
                    user_code:$(obj).children().children().attr("data-staff-code"),
                    user_name:$(obj).children().children().text().split(/\s+/)[1],
                    password:password

                };
                params.push(param);
            });

            console.log(params);
            $.ajax({
                type: 'post',
                url: getUrl()+'/Api/user/userself',
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                data: JSON.stringify(params),
                success: function (data) {

                    if (data.success) {
                        Notify('操作成功', 'top-right', '5000', 'primary', 'fa-times-circle', true);
                        user.refresh();
                    } else {
                        Notify('操作失败,'+data.message, 'top-right', '5000', 'danger', 'fa-times-circle', true);
                    }
                },
                error: function (data) {
                    Notify('操作失败,'+data.message, 'top-right', '5000', 'danger', 'fa-times-circle', true);
                }
            });

            $("#plcharge").modal('hide');
        });

    }

    user.refresh = function refresh() {
        var opt = {
            url:getUrl()+"/Api/user/retrieve",
            silent: true,
            query: JSON.stringify({
                user_code:$("#query_code").val(),
                user_name:$("#query_name").val(),
                role_name:$("#query_role_name").val(),
                limit: 10,
                offset: 0,
                type: 1,
                level: 2
            })
        };

        $("#table").bootstrapTable('refresh', opt);
    }


    //.初始化Table
    user.initTable();

    //.初始化Button的点击事件
    user.initButton();

});
var userVM = new Vue({
    el:'.user-vm',
    data:{
        pkDevice : [],pk_device:null,
    },
    mounted:function () {
        this.$nextTick(function () {
            this.getDevice();
        })
    },
    methods:{
        getDevice:function () {
            $.ajax({
                type: 'get',
                //data:JSON.stringify(pks),
                //dataType:"json",
                url:getUrl()+ '/Api/device/findAllretrieve',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    userVM.pkDevice = data.data;console.log(pkDevice)
                },
                error: function (data) {

                }
            });
        }
    }
});