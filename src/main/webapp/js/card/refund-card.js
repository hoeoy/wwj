$(function (refundcard) {
    document.getElementById("tree").style.maxHeight=$(window).height()+'px';
    var department_code;

    refundcard.initTreeView = function () {
        $.ajax({
            type: 'get',
            data: {company_code: "1"},
            dataType: "json",
            url: getUrl()+'/Api/dept/retrieveTree',
            contentType: "application/json;charset=UTF-8",
            success: function (data) {
                if (data.success) {
                    var nodes = JSON.stringify(listToTree(data.data));
                    $('#tree').treeview({
                        data: nodes,
                        onhoverColor:'#5bc0de',
                        onNodeSelected: function (event, node) {
                            $("#btn_batchcharge").removeAttr('disabled');
                            department_code = node.id;
                            refundcard.refresh();//刷新表格
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

    refundcard.initTable = function () {
        $('#table').bootstrapTable({
            url: getUrl()+'/Api/card/retrieve',         //请求后台的URL（*）
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
                    operator_type: "refund"
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
                field: 'card_state',
                title: '卡状态'
            }, {
                field: 'money_cash',
                title: '现金钱包'
            }, {
                field: 'money_allowance',
                title: '补贴钱包'
            }],
            onCheck: function () {
                var selected_btn = $('#table').bootstrapTable("getSelections");
                if (selected_btn.length == 1) {
                    $("#btn_refund").removeAttr('disabled');
                } else {
                    $("#btn_refund").attr('disabled', 'disabled');
                }
            },
            onUncheck: function () {
                var selected_btn = $('#table').bootstrapTable("getSelections");
                if (selected_btn.length == 1) {
                    $("#btn_refund").removeAttr('disabled');
                } else {
                    $("#btn_refund").attr('disabled', 'disabled');
                }
            },
            onCheckAll: function () {
                var selected_btn = $('#table').bootstrapTable("getSelections");
                if (selected_btn.length == 1) {
                    $("#btn_refund").removeAttr('disabled');
                } else {
                    $("#btn_refund").attr('disabled', 'disabled');
                }
            },
            onUncheckAll: function () {
                $("#btn_refund").attr('disabled', 'disabled');
            }
        });
        $('#table').bootstrapTable('hideColumn', 'pk_staff');
        $('#table').bootstrapTable('hideColumn', 'pk_card');
    }

    refundcard.initButton = function () {
        //查询
        $("#btn_query").click(function () {
            //console.log('查询');
            department_code = "";
            refundcard.refresh();
        });

        $("#btn_refund").click(function () {
            $("#info-modal").modal('show');
        });

        $("#okBtn_One").click(function () {

            var refund_money = $("#refund_money").val();

            if (refund_money == null || refund_money == "" || refund_money <= 0) {
                Notify('充值金额有误', 'top-right', '5000', 'info', 'fa-times-circle', true);
                return;
            }

            var selected_btn = $('#table').bootstrapTable("getSelections");

            var sendData = {
                pk_staff: selected_btn[0].pk_staff,
                pk_card: selected_btn[0].pk_card,
                staff_code: selected_btn[0].staff_code,
                refund_money: refund_money * 100,
                refund_type: "0",
                operator:JSON.parse(localStorage.user).pk_user
            };

            $.ajax({
                type: 'post',
                data: JSON.stringify(sendData),
                dataType: "json",
                url:getUrl()+ '/Api/card/refund',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.success) {
                        Notify('退款成功', 'top-right', '5000', 'primary', 'fa-times-circle', true);
                        refundcard.refresh();
                    } else {
                        Notify('退款失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                    }
                },
                error: function (data) {
                    Notify('退款失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                }
            });
            $("#info-modal").modal('hide');
            $("#refund_money").val("");
        });


    }

    refundcard.refresh = function refresh() {
        var opt = {
            url:getUrl()+ "/Api/card/retrieve",
            silent: true,
            query: JSON.stringify({
                department_code: department_code,
                operator_type: "refund",
                limit: 10,
                offset: 0,
                type: 1,
                level: 2
            })
        };

        $("#table").bootstrapTable('refresh', opt);
    }

    //1.初始化左侧树
    refundcard.initTreeView();

    //2.初始化Table
    refundcard.initTable();

    //3.初始化Button的点击事件
    refundcard.initButton();
    //读卡
    $("#btn_readcard").click(function(){
        var v ;

        v = AxRead.RequestPhyNo( ) ;
        if( v == 0 )
        {
            Notify('没有检测到卡或者发卡器', 'top-right', '5000', 'info', 'fa-times-circle', true);
        }
        else
        {
            $("#card_code").val(v);
        }
        $("#txt_search_card_code").val(v);
        department_code = "";
        refundcard.refresh();

        //chargecard.refresh();

    });

});
//部门树高度 背景色
(function(){
    setTimeout(function(){
        $('#tree').css({minHeight:deptTreeHeight,background:'#fff'});
    },500)
})();