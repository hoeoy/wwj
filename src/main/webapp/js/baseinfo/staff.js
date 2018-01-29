$(function (staff) {

    document.getElementById("tree").style.maxHeight=$(window).height()+'px';
    var department_code;
    var pk_staff;

    staff.initTreeView = function () {

        $.ajax({
            type: 'get',
            data: {company_code: "1"},
            dataType: "json",
            url:getUrl()+ '/Api/dept/retrieveTree',
            contentType: "application/json;charset=UTF-8",
            success: function (data) {
                if (data.success) {
                    var nodes = JSON.stringify(listToTree(data.data));
                    $('#tree').treeview({
                        data: nodes,
                        onNodeSelected: function (event, node) {
                            if(node.name == "dept"){
                                department_code = node.id;
                            }else{
                                department_code = "";
                            }

                            $("#btn_add").removeAttr('disabled');

                            //刷新表格
                            staff.refresh();

                        }
                    });
                } else {
                    alert("初始化部门树失败!:" + data.msg);
                }
            },
            error: function (data) {
                alert("初始化部门树失败!");
                onError();
            }
        });
    }

    staff.initTable = function () {
        $('#table').bootstrapTable({
            url:getUrl()+ '/Api/staff/retrieve',         //请求后台的URL（*）
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
                    staff_code: $("#txt_search_staff_code").val(),
                    staff_name: $("#txt_search_name").val(),
                    staff_type: $("#txt_search_staff_type").val(),
                    sex: $("#txt_search_sex").val(),
                    department_code: department_code,
                };
                return temp;
            },//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            //search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            showColumns: true,                  //是否显示所有的列
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
                field: 'pk_staff',
                title: ''
            },{
                field: 'staff_code',
                title: '编码'
            },{
                field: 'department_name',
                title: '部门'
            }, {
                field: 'staff_name',
                title: '姓名'
            }, {
                field: 'sex',
                title: '性别'
            },{
                field: 'staff_type',
                title: '人员类别'
            },
               /* {
                field: 'id_card',
                title: '身份证'
            }, */{
                field: 'birth_date',
                title: '生日'
            },{
                field: 'hire_date',
                title: '入职日期'
            },{
                field: 'leave_date',
                title: '离职日期'
                }, {
                    field: 'update_date',
                    title: '修改时间'
                }, {
                    field: 'phone',
                    title: '联系电话'
                }, {
                field: 'email',
                title: '邮箱'
            }],
            onCheck:function(){
                var selected_btn=$('#table').bootstrapTable("getSelections");
                if(selected_btn.length ==1){
                    $("#btn_edit").removeAttr('disabled');
                    $("#btn_delete").removeAttr('disabled');
                    $("#changeDept").removeAttr('disabled');
                }else if(selected_btn.length>1){
                    $("#btn_edit").attr('disabled','disabled');
                    $("#changeDept").attr('disabled','disabled');
                    $("#btn_delete").removeAttr('disabled');
                }
            },
            onUncheck:function(){
                var selected_btn=$('#table').bootstrapTable("getSelections");
                if(selected_btn.length ==1){
                    $("#btn_edit").removeAttr('disabled');
                    $("#btn_delete").removeAttr('disabled');
                    $("#changeDept").removeAttr('disabled');
                }else if(selected_btn.length>1){
                    $("#btn_edit").attr('disabled','disabled');
                    $("#changeDept").attr('disabled','disabled');
                    $("#btn_delete").removeAttr('disabled');
                }else if(selected_btn.length==0){
                    $("#btn_edit").attr('disabled','disabled');
                    $("#btn_delete").attr('disabled','disabled');
                    $("#changeDept").attr('disabled','disabled');
                }
            },
            onCheckAll:function(){
                var selected_btn=$('#table').bootstrapTable("getSelections");
                if(selected_btn.length ==1){
                    $("#btn_edit").removeAttr('disabled');
                    $("#btn_delete").removeAttr('disabled');
                }else if(selected_btn.length>1){
                    $("#btn_edit").attr('disabled','disabled');
                    $("#btn_delete").removeAttr('disabled');
                }else if(selected_btn.length==0){
                    $("#btn_edit").attr('disabled','disabled');
                    $("#btn_delete").attr('disabled','disabled');
                }
            },
            onUncheckAll:function(){
                $("#btn_edit").attr('disabled','disabled');
                $("#btn_delete").attr('disabled','disabled');
            }
        });
        $('#table').bootstrapTable('hideColumn', 'pk_staff');
        $('#table').bootstrapTable('hideColumn', 'pk_card');
    }

    staff.initButton = function () {
        //查询
        $("#btn_query").click(function () {
            staff.refresh();
            //alert($("#txt_search_sex").val())
        });

       //增加
        $("#btn_add").click(function(){
            if(department_code == null){
                Notify('请选择部门', 'top-right', '5000', 'info', 'fa-times-circle', true);
                return;
            }
            console.log(department_code);
            $("#staff_code").removeAttr("readonly");
            $("#staff_code").val("");
            $("#department_name").val(department_code);
            $("#staff_name").val("");
            $("#sex").val("1");
            $("#statu").val("1");
            $("#id_card").val("");
            $("#birth_date").val("");
            $("#hire_date").val("");
            $("#phone").val("");
            $("#leave_date").val("");
            $("#email").val("");
            pk_staff = null;

            $("#mymodal-data").modal('show');
        });

        $("#sync").click(function(){
            $("#zz").show();
            $("#tb_load").show();
            $.ajax({
                type: 'get',
                dataType: "json",
                url:getUrl()+ '/Api/task/syncnow',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if(data.success == true){
                        Notify('同步成功', 'top-right', '5000', 'primary', 'fa-times-circle', true);
                        $("#zz").hide();
                        $("#tb_load").hide();
                    }else{
                        Notify('同步失败,'+data.message, 'top-right', '5000', 'danger', 'fa-times-circle', true);
                        $("#zz").hide();
                        $("#tb_load").hide();
                    }

                },
                error: function (data) {
                    Notify('同步失败,'+data.message, 'top-right', '5000', 'danger', 'fa-times-circle', true);
                    $("#zz").hide();
                    $("#tb_load").hide();
                }
            });
        });

        //修改
        $("#btn_edit").click(function(){
            var selected = $('#table').bootstrapTable("getSelections");
            $("#staff_code").attr("readonly","readonly");
            $("#staff_code").val(selected[0].staff_code);
            $("#department_name").val(selected[0].department_name);
            $("#staff_name").val(selected[0].staff_name);
            $("#sex").val(selected[0].sex == "男" ? "1" : (selected[0].sex == "女" ? "0" : ""));
            $("#status").val(selected[0].staff_type == "在职" ? "1" : (selected[0].staff_type == "离职" ? "0" : "") );
            $("#id_card").val(selected[0].id_card);
            $("#birth_date").val(selected[0].birth_date);
            $("#hire_date").val(selected[0].hire_date);
            $("#leave_date").val(selected[0].leave_date);
            $("#phone").val(selected[0].phone);
            $("#email").val(selected[0].email);
            pk_staff = selected[0].pk_staff;
            staffVue.merchants  = selected[0].pk_merchant;
            //console.log(selected[0].pk_merchant,staffVue.merchants)
            $("#mymodal-data").modal('show');


        });



        $("#pldismiss").click(function(){
            $("#changeDeptModel").modal('hide');
        });

        $("#changeDept").click(function(){

            $.ajax({
                type: 'get',
                data: {company_code: "1"},
                dataType: "json",
                url:getUrl()+ '/Api/dept/retrieveTree',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.success) {
                        var nodes = JSON.stringify(listToTree(data.data));
                        $('#changeDeptTree').treeview({
                            data: nodes,
                            onNodeSelected: function (event, node) {
                                if(node.name == "dept"){
                                    department_code = node.id;
                                }else{
                                    department_code = "";
                                }
                            }
                        });

                        $("#changeDeptModel").modal('show');
                    } else {
                        alert("初始化部门树失败!:" + data.msg);
                    }
                },
                error: function (data) {
                    alert("初始化部门树失败!");
                    onError();
                }
            });


        });

        //删除
        $("#btn_delete").click(function(){
            var selected = $('#table').bootstrapTable("getSelections");

            var sendData = [];

            $.each(selected,function(idx,obj){
                sendData.push(obj.pk_staff);
            });

            $.ajax({
                type: 'post',
                data: JSON.stringify(sendData),
                dataType: "json",
                url:getUrl()+ '/Api/staff/delete',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.success) {
                        Notify('删除成功', 'top-right', '5000', 'primary', 'fa-times-circle', true);
                        staff.refresh();
                    } else {
                        Notify('删除失败' + data.msg, 'top-right', '5000', 'danger', 'fa-times-circle', true);

                    }
                },
                error: function (data) {
                    Notify('删除失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                }
            });

        });

    }

    staff.refresh = function refresh() {
        var opt = {
            url:getUrl()+"/Api/staff/retrieve",
            silent: true,
            query: JSON.stringify({
                department_code: department_code,
                staff_code: $("#txt_search_staff_code").val(),
                staff_name: $("#txt_search_name").val(),
                staff_type: $("#txt_search_staff_typee").val(),
                sex: $("#txt_search_sex").val(),
                limit: 10,
                offset: 0,
                type: 1,
                level: 2
            })
        };

        $("#table").bootstrapTable('refresh', opt);
    }

    //1.初始化左侧树
    staff.initTreeView();

    //2.初始化Table
    staff.initTable();

    //3.初始化Button的点击事件
    staff.initButton();

    var staffVue=new Vue({
        el:"#allcontent",
        data:{
            choice_numbers:0,
            success_number:'',
            fail_number:'',
            errCode:'',
            merchantArr:[],
            data:[],
            bulkEdit:[],pk_merchant:null,merchants:null,
        },
        mounted:function () {
            this.$nextTick(function () {
                this.getDept();
                this.getMerchant();
            })
        },
        methods:{
            close_x:function(){
                $("#infor").hide();
                $("#zz").hide();
            },
            save:function(){
                console.log("save");
                var staff_code = $("#staff_code").val();
                var staff_name = $("#staff_name").val();
                var pk_merchant = $("#merchants").val()
                if(staff_code == null || staff_code == "" || staff_name == null || staff_name == "" ){
                    Notify('请填写完整信息', 'top-right', '5000', 'info', 'fa-times-circle', true);
                    return;
                }
                if(pk_merchant == null || pk_merchant == ""){
                    Notify('请选择所属商户', 'top-right', '5000', 'info', 'fa-times-circle', true);
                    return;
                }
                var sendData = {
                    pk_staff:pk_staff,
                    staff_code:staff_code,
                    staff_name:staff_name,
                    department_code:department_code,
                    sex:$("#sex").val(),
                    id_card:$("#id_card").val(),
                    birth_date:$("#birth_date").val(),
                    hire_date:$("#hire_date").val(),
                    leave_date:$("#leave_date").val(),
                    phone:$("#phone").val(),
                    staff_type:$("#status").val(),
                    email:$("#email").val(),
                    pk_merchant:pk_merchant,
                };
                $.ajax({
                    type: 'post',
                    data: JSON.stringify(sendData),
                    dataType: "json",
                    url:getUrl()+ '/Api/staff/save',
                    contentType: "application/json;charset=UTF-8",
                    success: function (data) {
                        console.log(data);
                        if (data.success) {
                            Notify('保存成功', 'top-right', '5000', 'primary', 'fa-times-circle', true);
                            staff.refresh();
                            $("#mymodal-data").modal('hide');
                        } else {
                            Notify('保存失败' + data.message, 'top-right', '5000', 'danger', 'fa-times-circle', true);
                        }
                    },
                    error: function (data) {
                        Notify('保存失败' , 'top-right', '5000', 'danger', 'fa-times-circle', true);
                    }
                });

            },
            plokBtn:function(){
                //$('#surechangeDeptModel').modal({backdrop: 'static', keyboard: false});
                //$('#changeDeptModel').modal({backdrop: 'static', keyboard: false});
                $("#surechangeDeptModel").modal('hide');

                var selected = $('#table').bootstrapTable("getSelections");
                if(selected[0].department_code == department_code){
                    Notify('部门没有发生变化', 'top-right', '5000', 'info', 'fa-exclamation-circle', true);
                    $("#changeDeptModel").modal('hide');
                    return;
                }else if(department_code == null || department_code==""){
                    Notify('没有选择部门', 'top-right', '5000', 'error', 'fa-times-circle', true);
                    $("#changeDeptModel").modal('hide');
                    return;
                }else{
                    var sendData = {
                        pk_staff:selected[0].pk_staff,
                        department_code:department_code
                    };

                    $.ajax({
                        type: 'post',
                        data: JSON.stringify(sendData),
                        dataType: "json",
                        url:getUrl()+ '/Api/staff/save',
                        contentType: "application/json;charset=UTF-8",
                        success: function (data) {
                            console.log(data);
                            if (data.success) {
                                Notify('保存成功', 'top-right', '5000', 'primary', 'fa-times-circle', true);
                                staff.refresh();
                            } else {
                                Notify('保存失败' + data.message, 'top-right', '5000', 'danger', 'fa-times-circle', true);
                            }
                        },
                        error: function (data) {
                            Notify('保存失败' , 'top-right', '5000', 'danger', 'fa-times-circle', true);
                        }
                    });
                    $("#changeDeptModel").modal('hide');
                }
            },
            query:function (dept) {
                $.ajax({
                    type: 'get',
                    url: getUrl() + "/Api/staff/findAllForupd",
                    data:{department_code: dept},
                    //dataType: "json",
                    contentType: "application/json;charset=UTF-8",
                    success: function (data) {
                        staffVue.data = data.data;

                    },
                    error: function (data) {
                        Notify('获取数据失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                    }
                })
            },
            getMerchant:function(){//获取 部门
                $.ajax({
                    type: 'get',
                    data: {
                        limit:10,
                        offset:0,
                    },
                    dataType: "json",
                    url:getUrl()+ '/Api/merchant/retrieve',
                    //contentType: "application/json;charset=UTF-8",
                    success: function (data) {
                        staffVue.merchantArr = data.rows//merchant_name.pk_merchant
                        console.log(staffVue.merchantArr)
                    },
                    error: function (data) {
                        console.log("初始化部门树失败!");
                        //	onError();
                    }
                });
            },
            getDept:function(){//获取 部门

                $.ajax({
                    type: 'get',
                    data: {},
                    dataType: "json",
                    url:getUrl()+ '/Api/dept/retrieveTree',
                    contentType: "application/x-www-form-urlencoded",
                    success: function (data) {
                        if (data.success) {
                            //console.log( 'charge '+data.data);
                            staffVue.showDept( JSON.stringify(listToTree(data.data)));
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
            showDept:function(nodes){
                //var nodes = sessionStorage.deptTree;
                //var nodes = sessionStorage.deptTree;
                $('#dept_tree').treeview({
                    data: nodes,
                    onhoverColor:'#5bc0de',
                    onNodeSelected: function (event, node) {
                        staffVue.query(node.id);
                    },
                    onNodeUnselected: function (event, node){

                    }
                });
            },
            saveBulkEdit:function () {/*/Api/staff/batchupdate */
                if(this.pk_merchant == '' || this.pk_merchant == null || this.pk_merchant == undefined ){
                    Notify('请选择所属商户', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                    return;
                }
                var data = {
                    pk_merchant:staffVue.pk_merchant,
                    pk_staff:staffVue.bulkEdit
                }
                $.ajax({
                    type: 'POST',
                    url: getUrl() + "/Api/staff/batchupdate",
                    data: {"data": JSON.stringify(data)},
                    dataType: "json",
                    contentType: "application/x-www-form-urlencoded",
                    success: function (data) {
                        if (data.success) {
                            Notify('保存成功', 'top-right', '5000', 'primary', 'fa-times-circle', true);
                            staff.refresh();
                            $("#myModal").modal('hide');
                        } else {
                            Notify('保存失败' + data.message, 'top-right', '5000', 'danger', 'fa-times-circle', true);
                        }

                    },
                    error: function (data) {
                        Notify('获取数据失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                    }
                })
            },
        },
        components:{
            'my-transfer':{
                template:'\n                <div class="el-transfer">\n              <div class="el-transfer-panel" >\n                    <p class="el-transfer-panel__header text-center">待  选</p>\n                    <div class="el-transfer-panel__body">\n                        <div class="el-checkbox-group el-transfer-panel__list" style="">\n                                 <label class="el-checkbox el-transfer-panel__item" @click="LeftToggleChecked(item)" v-for="(item,index) in leftData">\n                                        <span class="el-checkbox__input" :class="{\'is-checked\':item.isChecked}" >\n                                              <span class="el-checkbox__inner"></span>\n                                        </span>\n                                        <span class="el-checkbox__label">\n                                                <span>{{item.staff_name}}</span><span style="position: absolute; right: 15px;">{{item.staff_code}}</span>\n                                        </span>\n                                </label>\n                                <label class="el-checkbox el-transfer-panel__item" @click="rightToggleChecked(item)" v-if="index == leftData.length-1"   v-for="(item,index) in leftData">\n                                        <span class="el-checkbox__input" :class="{\'is-checked\':item.isChecked}">\n                                              <span class="el-checkbox__inner"></span>\n                                        </span>\n                                        <span class="el-checkbox__label">\n                                                <span>{{index}}{{item.staff_name}}</span><span style="position: absolute; right: 15px;">{{item.staff_code}}</span>\n                                        </span>\n                                </label>\n                        </div>\n                        <p class="el-transfer-panel__empty" style="display: none;">无匹配数据</p>\n                        <p class="el-transfer-panel__empty" style="position: absolute;top: 75px;padding-left: 75px;" v-if="leftData.length == 0">无数据</p>\n                    </div>\n                    <p class="el-transfer-panel__footer">\n                      <label class="el-checkbox" @click.prevent="leftToggleCheckAll">\n                                    <span class="el-checkbox__input" :class="{\'is-checked\':isLeftChecked}">\n                                         <span  class="el-checkbox__inner"></span>\n                                         <input type="checkbox"  class="el-checkbox__original" value="">\n                                    </span>\n                        <span  class="el-checkbox__label">共 {{leftData.length}} 项</span>\n                      </label>\n                    </p>\n              </div>\n              <div class="el-transfer__buttons">\n                  <button  type="button" class="el-button el-button--primary el-button--small"\n                            :class="{\'is-disabled\':rightData.length == 0}"\n                            @click="pushToLeft">\n                        <span><i class="el-icon-arrow-left"></i></span>\n                  </button>\n                  <button type="button" class="el-button el-button--primary el-button--small"\n                            :class="{\'is-disabled\':leftData.length == 0}"\n                            @click="pushToRight">\n                        <span><i class="el-icon-arrow-right"></i></span>\n                  </button>\n              </div>\n              <div class="el-transfer-panel">\n                    <p class="el-transfer-panel__header text-center">已  选</p>\n                    <div class="el-transfer-panel__body">\n                      <div class="el-checkbox-group el-transfer-panel__list" style="">\n                            \n                                <label class="el-checkbox el-transfer-panel__item" @click="rightToggleChecked(item)"   v-for="(item,index) in rightData">\n                                        <span class="el-checkbox__input" :class="{\'is-checked\':item.isChecked}">\n                                              <span class="el-checkbox__inner"></span>\n                                        </span>\n                                        <span class="el-checkbox__label">\n                                                <span>{{index}}{{item.staff_name}}</span><span style="position: absolute; right: 15px;">{{item.staff_code}}</span>\n                                        </span>\n                                </label>\n                                <label class="el-checkbox el-transfer-panel__item" @click="rightToggleChecked(item)" v-if="index == rightData.length-1"   v-for="(item,index) in rightData">\n                                        <span class="el-checkbox__input" :class="{\'is-checked\':item.isChecked}">\n                                              <span class="el-checkbox__inner"></span>\n                                        </span>\n                                        <span class="el-checkbox__label">\n                                                <span>{{index}}{{item.staff_name}}</span><span style="position: absolute; right: 15px;">{{item.staff_code}}</span>\n                                        </span>\n                                </label>\n                                                        \n                      </div>                      \n                      <p class="el-transfer-panel__empty" style="position: absolute;top: 75px;padding-left: 75px;" v-if="rightData.length == 0">无数据</p>\n                    </div>\n                  <p class="el-transfer-panel__footer">\n                    <label class="el-checkbox" @click.prevent="rightToggleCheckAll">\n                               <span class="el-checkbox__input" :class="{\'is-checked\':isRightChecked}">\n                                   <span class="el-checkbox__inner"></span>\n                               </span>\n                      <span class="el-checkbox__label">共 {{rightData.length}} 项</span>\n                    </label>\n                  </p>\n              </div>\n    </div>',
                props:['data','title','value'],
                data:function () {
                    return {
                        leftData:[],rightData:[],leftChecked:[],rightChecked:[],isLeftChecked:false,isRightChecked:false,result:[]
                    }
                },
                computed:{
                    /*resource:function (val) {
                        console.log(this.data)
                      /!*  this.leftData = this.data.map(function (item) {
                            return item;
                        });
                        return this.data;*!/
                    },*/
                },
                watch:{
                    data:function (val) {
                        this.leftData = JSON.parse(JSON.stringify(this.data))
                    },

                },
                mounted:function () {
                    this.$nextTick(function () {



                    })
                },
                methods:{
                    /*unique:function(array){
                        var r = [];
                        for(var i = 0, l = array.length; i < l; i++) {
                            for(var j = i + 1; j < l; j++)
                                if (array[i] === array[j]) j = ++i;
                            r.push(array[i]);
                        }
                        return r;
                    },*/
                    LeftToggleChecked:function (item) {//console.log(item)
                        if(item.isChecked == undefined){
                            this.$set(item,'isChecked',true);
                        }else{
                            item.isChecked = !item.isChecked;
                        }
                        if(item.isChecked){
                            this.leftChecked.push(item);
                        }else{
                            if(this.leftChecked.indexOf(item) != -1){
                                this.leftChecked.splice(this.leftChecked.indexOf(item),1)
                            }
                        }
                    },
                    rightToggleChecked:function (item) {//console.log(item)
                        if(item.isChecked == undefined){
                            this.$set(item,'isChecked',true);
                        }else{
                            item.isChecked = !item.isChecked;
                        }
                        if(item.isChecked){
                            this.rightChecked.push(item);
                        }else{
                            if(this.leftChecked.indexOf(item) != -1){
                                this.rightChecked.splice(this.rightChecked.indexOf(item),1)
                            }
                        }
                    },
                    pushToRight:function () {
                        var arr = this.clearChecked(this.leftChecked);
                        this.rightData = this.rightData.concat(arr);
                        this.rightData = this.rightData;
                        this.clearState('leftToRight');
                        this.getResult();
                    },
                    pushToLeft:function () {
                        var arr = this.clearChecked(this.rightChecked);
                        this.leftData = this.leftData.concat(this.rightChecked);
                        this.leftData = this.leftData;
                        this.clearState('rightToLeft');
                        this.getResult();
                    },
                    clearState:function (direction) {
                        var resource,target;
                        if(direction == 'leftToRight'){
                            resource = this.leftChecked,target = this.leftData;
                        }else{
                            resource = this.rightChecked,target = this.rightData;
                        }
                        resource.forEach(function (item,index) {
                            target.splice(target.indexOf(item),1);
                        })
                        resource.length = 0;this.isLeftChecked = this.isRightChecked = false;
                    },
                    clearChecked:function (arrObj) {
                        var arr;
                        arr = arrObj.map(function (item) {
                            item.isChecked = false;
                            return item;
                        });
                        return arr;
                    },
                    leftToggleCheckAll:function () {
                        this.isLeftChecked = !this.isLeftChecked;
                        if(this.isLeftChecked){
                            var _this = this;
                            this.leftData.forEach(function (item) {
                                if(item.isChecked == undefined){
                                    _this.$set(item,'isChecked',true);
                                }else{
                                    item.isChecked = true;
                                }
                            })
                            this.leftChecked = this.leftData.map(function (items) {
                                return items;
                            });
                        }else{
                            this.leftData.forEach(function (item) {
                                item.isChecked = false;
                            })
                            this.leftChecked.length = 0;
                        }
                    },
                    rightToggleCheckAll:function () {
                        this.isRightChecked = !this.isRightChecked;
                        if(this.isRightChecked){
                            this.rightData.forEach(function (item) {
                                item.isChecked = true;
                            })
                            this.rightChecked = this.rightData.map(function (items) {
                                return items;
                            });
                        }else{
                            this.rightData.forEach(function (item) {
                                item.isChecked = false;
                            })
                            this.rightChecked.length = 0;
                        }
                    },
                    getResult:function () {
                        this.result = this.rightData.map(function (item) {
                                return item.pk_staff;
                            }
                        );
                        this.$emit('input', this.result)
                    }
                }
            }
        }
    });

    $("#inputExcel").submit(function(){
        $("#loading").show();
        $("#zz").show();
        $("#inputExcel").ajaxSubmit({
            success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
                console.log(data);
                staffVue.success_number=data.status;
                staffVue.errCode=data.errCode;
                var times=new Date();
                var time=times.toLocaleDateString();
                localStorage.infor=data.operateCode;
                $("#uploadExcelModal").modal("hide");
                $("#loading").hide();
                $("#infor").show();
                $("#zz").show();
                if(data.success){

                }else{
                   /* Notify("导入失败", 'top-right', '5000', 'danger', 'fa-times-circle', true);
                    $("#loading").hide();
                    $("#zz").hide();*/
                }
            }

        });
        return false;
    });
});




