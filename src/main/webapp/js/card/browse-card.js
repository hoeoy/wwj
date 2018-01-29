$(function (browsecard) {
    document.getElementById("tree").style.maxHeight=$(window).height()+'px';
    var department_code;

    browsecard.initTreeView = function () {
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
                        onhoverColor:'#5bc0de',
                        onNodeSelected: function (event, node) {
                            department_code = node.id;
                            browsecard.refresh(); //刷新表格
                        },
                        onNodeUnselected: function (event, node){
                            department_code = '';
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

    browsecard.initTable = function () {
        $('#table').bootstrapTable({
            url:getUrl()+ '/Api/card/retrieve',         //请求后台的URL（*）
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
                    card_code: $("#txt_search_card_code").val(),
                    department_code: department_code,
                    operator_type: "browse"
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
            }, {
                field: 'pk_card',
                title: ''
            },{
                field: 'pk_meal_rule',
                title: ''
            }, {
                field: 'staff_code',
                title: '编码'
            }, {
                field: 'staff_name',
                title: '姓名'
            }, {
                field: 'card_code',
                title: '卡号'
            }, {
                field: 'meal_rule_name',
                title: '消费规则'
            }, {
                field: 'card_state',
                title: '卡状态'
            }, {
                field: 'card_costing',
                title: '卡成本'
            },{
                field: 'card_deposit',
                title: '卡押金'
            },{
                field: 'password',
                title: '密码'
            },{
                field: 'money_cash',
                title: '现金钱包'
            }, {
                field: 'money_allowance',
                title: '补贴钱包'
            }, {
                field: 'card_ineffectived_ts',
                title: '有效期'
            }],
            onCheck:function(row){
                browseVM.rows.push(row);
                var selected_btn=$('#table').bootstrapTable("getSelections");
                if(selected_btn.length ==1){
                    $("#btn_edit").removeAttr('disabled');
                }else{
                    $("#btn_edit").attr('disabled','disabled');
                }
            },
            onUncheck:function(row){
                browseVM.rows.splice(browseVM.rows.indexOf(row),1);
                var selected_btn=$('#table').bootstrapTable("getSelections");
                if(selected_btn.length ==1){
                    $("#btn_edit").removeAttr('disabled');
                }else{
                    $("#btn_edit").attr('disabled','disabled');
                }
            },
            onCheckAll:function(){
                var selected_btn=$('#table').bootstrapTable("getSelections");
                if(selected_btn.length ==1){
                    $("#btn_edit").removeAttr('disabled');
                }else{
                    $("#btn_edit").attr('disabled','disabled');
                }
            },
            onUncheckAll:function(){
                $("#btn_edit").attr('disabled','disabled');
            }
        });
        $('#table').bootstrapTable('hideColumn', 'pk_staff');
        $('#table').bootstrapTable('hideColumn', 'pk_card');
    }

    browsecard.initButton = function () {
        //查询
        $("#btn_query").click(function () {
            department_code = "";
            browsecard.refresh();
        });

        $("#btn_edit").click(function(){

            $("#pk_meal_rule").html("");

            $.ajax({
                type: 'post',
                dataType:"json",
                url:getUrl()+ '/Api/mealrule/retrieveAll',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.success) {

                        var selected_btn=$('#table').bootstrapTable("getSelections");

                        $("#pk_card").val(selected_btn[0].pk_card);
                        $("#card_code").val(selected_btn[0].card_code);
                        $("#staff_code").val(selected_btn[0].staff_code);
                        $("#staff_name").val(selected_btn[0].staff_name);
                        $("#card_deposit").val(selected_btn[0].card_deposit);
                        $("#card_costing").val(selected_btn[0].card_costing);
                        $("#pk_meal_rule").val(selected_btn[0].pk_meal_rule);
                        $("#password").val(selected_btn[0].password);
                        $("#card_ineffectived_ts").val(selected_btn[0].card_ineffectived_ts);
                        browseVM.checkList = [];
                        var def1 = selected_btn[0].def1;//'1,2,3,4';//
                        if(typeof def1 == 'string'){
                            def1 = def1.split(",")
                            def1.map(function (t) {
                                switch(t)
                                {
                                    case '0':
                                        browseVM.checkList.push('早餐')
                                        break;
                                    case '1':
                                        browseVM.checkList.push('午餐')
                                        break;
                                    case '2':
                                        browseVM.checkList.push('外卖')
                                        break;
                                    case '3':
                                        browseVM.checkList.push('晚餐')
                                        break;
                                    case '4':
                                        browseVM.checkList.push('夜宵')
                                        break;
                                }
                            });
                        }
                        //console.log(selected_btn[0])

                        $.each(data.data,function(idx,obj){
                            if(obj.pk_meal_rule == selected_btn[0].pk_meal_rule){
                                $("#pk_meal_rule").append("<option value='"+obj.pk_meal_rule+"' selected='selected'>"+obj.meal_rule_name+"</option>");
                            }else{
                                $("#pk_meal_rule").append("<option value='"+obj.pk_meal_rule+"'>"+obj.meal_rule_name+"</option>");
                            }
                        });

                        $("#info-modal").modal('show');

                    } else {
                        Notify('获取消费规则失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                    }
                },
                error: function (data) {
                    Notify('获取消费规则失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                }
            });

        });

        $("#okBtn_One").click(function(){console.log(111)
            //browseVM.checkList = ['早餐','午餐','午餐']
           /* console.log( browseVM.checkList);
            var def1 = [];
            browseVM.checkList.map(function (t) {
                switch(t)
                {
                    case '早餐':
                        def1.push('0')
                        break;
                    case '午餐':
                        def1.push('1')
                        break;
                    case '外卖':
                        def1.push('2')
                        break;
                    case '晚餐':
                        def1.push('3')
                        break;
                    case '夜宵':
                        def1.push('4')
                        break;
                }
            });
            console.log(def1)*/
            /*var sendData = {
                pk_card:$("#pk_card").val(),
                pk_meal_rule:$("#pk_meal_rule").val(),
                password:$("#password").val(),
                card_ineffectived_ts:$("#card_ineffectived_ts").val(),
            };

            $.ajax({
                type: 'post',
                data:JSON.stringify(sendData),
                dataType:"json",
                url:getUrl()+ '/Api/card/updateCardInfo',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.success) {
                        Notify('保存成功', 'top-right', '5000', 'primary', 'fa-times-circle', true);
                        browsecard.refresh();
                    } else {
                        Notify('保存失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                    }
                },
                error: function (data) {
                    Notify('保存失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                }
            });
            $("#info-modal").modal('hide');*/
        });

        //读卡
        $("#btn_read_card").click(function () {

            //调取硬件读取卡号
            var v ;

            v = AxRead.RequestPhyNo() ;
            if( v == 0 )
            {
                Notify('没有检测到卡或者发卡器', 'top-right', '5000', 'danger', 'fa-times-circle', true);
            }
            else
            {
                $("#txt_search_card_code").val(v);
                department_code = "";
            }

            browsecard.refresh();
        });



    }

    browsecard.refresh = function refresh() {
        var opt = {
            url:getUrl()+"/Api/card/retrieve",
            silent: true,
            query: JSON.stringify({
                department_code: department_code,
                operator_type: "browse",
                limit: 10,
                offset: 0,
                type: 1,
                level: 2
            })
        };

        $("#table").bootstrapTable('refresh', opt);
    }

    //1.初始化左侧树
    browsecard.initTreeView();

    //2.初始化Table
    browsecard.initTable();

    //3.初始化Button的点击事件
    browsecard.initButton();

    var browseVM = new Vue({
        el:'#info-modal',
        data:{
            checkList:[],rows:[],
        },
        mounted:function () {
            this.$nextTick(function () {

            })
        },
        methods:{
            save:function () {
                // console.log( browseVM.checkList);
                var def1 = [];
                browseVM.checkList.map(function (t) {
                    switch(t)
                    {
                        case '早餐':
                            def1.push('0')
                            break;
                        case '午餐':
                            def1.push('1')
                            break;
                        case '外卖':
                            def1.push('2')
                            break;
                        case '晚餐':
                            def1.push('3')
                            break;
                        case '夜宵':
                            def1.push('4')
                            break;
                    }
                });
                def1 = def1.join(",")
                /*console.log(def1)
                console.log(typeof  def1)*/
                var sendData = {
                    pk_card:$("#pk_card").val(),
                    pk_meal_rule:$("#pk_meal_rule").val(),
                    password:$("#password").val(),
                    card_ineffectived_ts:$("#card_ineffectived_ts").val(),
                    def1:def1,
                };

                $.ajax({
                    type: 'post',
                    data:JSON.stringify(sendData),
                    dataType:"json",
                    url:getUrl()+ '/Api/card/updateCardInfo',
                    contentType: "application/json;charset=UTF-8",
                    success: function (data) {
                        if (data.success) {
                            Notify('保存成功', 'top-right', '5000', 'primary', 'fa-times-circle', true);
                            browsecard.refresh();
                        } else {
                            Notify('保存失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                        }
                    },
                    error: function (data) {
                        Notify('保存失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                    }
                });
                $("#info-modal").modal('hide');
            }
        },
    });

});
//部门树高度 背景色
(function(){
    setTimeout(function(){
        $('#tree').css({minHeight:deptTreeHeight,background:'#fff'});
    },500)
})();