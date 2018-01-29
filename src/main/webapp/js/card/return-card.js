$(function (returncard) {
    document.getElementById("tree").style.maxHeight=$(window).height()+'px';
    var department_code;

    returncard.initTreeView = function () {
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
                            $("#btn_batchcharge").removeAttr('disabled');
                            department_code = node.id;
                            returncard.refresh(); //刷新表格
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

    returncard.initTable = function () {
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
                    operator_type: "return"
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
                    $("#btn_return").removeAttr('disabled');
                } else {
                    $("#btn_return").attr('disabled', 'disabled');
                }
            },
            onUncheck: function () {
                var selected_btn = $('#table').bootstrapTable("getSelections");
                if (selected_btn.length == 1) {
                    $("#btn_return").removeAttr('disabled');
                } else {
                    $("#btn_return").attr('disabled', 'disabled');
                }
            },
            onCheckAll: function () {
                var selected_btn = $('#table').bootstrapTable("getSelections");
                if (selected_btn.length == 1) {
                    $("#btn_return").removeAttr('disabled');
                } else {
                    $("#btn_return").attr('disabled', 'disabled');
                }
            },
            onUncheckAll: function () {
                $("#btn_return").attr('disabled', 'disabled');
            }
        });
        $('#table').bootstrapTable('hideColumn', 'pk_staff');
        $('#table').bootstrapTable('hideColumn', 'pk_card');
    }

    returncard.initButton = function () {
        //查询
        $("#btn_query").click(function () {
            department_code = "";
            returncard.refresh();
        });

        $("#btn_return").click(function () {
            $("#info-modal").modal('show');
        });

        $("#okBtn_One").click(function () {

            var selected = $('#table').bootstrapTable("getSelections");

            var be_return_cash = $('#be_return_cash').is(':checked') ? true : false;
            var be_return_allowance = $('#be_return_allowance').is(':checked') ? true : false;
            var be_return_deposit = $('#be_return_deposit').is(':checked') ? true : false;

            $.ajax({
                type: 'post',
                data: JSON.stringify([{
                    pk_staff:selected[0].pk_staff,
                    pk_card:selected[0].pk_card,
                    card_code:selected[0].card_code,
                    staff_code:selected[0].staff_code,
                    be_return_cash:be_return_cash,
                    be_return_allowance:be_return_allowance,
                    be_return_deposit:be_return_deposit,
                    operator:JSON.parse(localStorage.user).pk_user,
                }]),
                dataType: "json",
                url: getUrl()+'/Api/card/return',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.success) {
                        Notify('退卡成功', 'top-right', '5000', 'primary', 'fa-times-circle', true);
                        returncard.refresh();
                    } else {
                        Notify('退卡失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                    }
                },
                error: function (data) {
                    Notify('退卡失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                }
            });
            $("#info-modal").modal('hide');
        });

        $("#btn_readcard").click(function(){
            //调取硬件读取卡号
            var v ;

            v = AxRead.RequestPhyNo( ) ;
            if( v == 0 )
            {
                Notify('没有检测到卡或者发卡器', 'top-right', '5000', 'info', 'fa-times-circle', true);
            }
            else
            {
                $("#txt_search_card_code").val(v);
            }

            department_code = "";
            returncard.refresh();

        });

        $("#btn_batchreturn").click(function () {
            $("#left_psns ul").html("");
            $("#right_psns ul").html("");
            $("#plcharge").modal('show');

            $.ajax({
                type: 'get',
                data: {company_code: "1"},
                dataType: "json",
                url: getUrl()+'/Api/dept/retrieveTree',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.success) {
                        var nodes = JSON.stringify(listToTree(data.data));
                        $('#left-tree').treeview({
                            data: nodes,
                            onhoverColor:'#5bc0de',
                            onNodeSelected: function (event, node) {
                                $.ajax({
                                    type: 'get',
                                    url: getUrl()+"/Api/card/retrieve",
                                    data: {department_code: node.id, operator_type: "return", offset: 0, limit: 9999},
                                    dataType: "json",
                                    contentType: "application/json;charset=UTF-8",
                                    success: function (data) {
                                        $("#left_psns ul").html("");
                                        $.each(data.rows, function (idx, obj) {
                                            $("#left_psns ul").append("<li class='list-group-item'><a href='#'><span  data-pk-staff='" + obj.pk_staff + "' data-pk-card='" + obj.pk_card + "' data-card-code='" + obj.card_code + "' data-staff-code='" + obj.staff_code + "'>" + obj.staff_name + "</span></a></li>");
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

        });

        $("#plokBtn").click(function(){

            var params = [];

            var be_return_cash = $('#be_return_cash_pl').attr('checked') ? true : false;
            var be_return_allowance = $('#be_return_allowance_pl').attr('checked') ? true : false;
            var be_return_deposit = $('#be_return_deposit_pl').attr('checked') ? true : false;

            var batchChargeItems = $("#right_psns ul").children();
            $.each(batchChargeItems,function(idx,obj){
                var param = {
                    pk_staff:$(obj).children().children().attr("data-pk-staff"),
                    pk_card:$(obj).children().children().attr("data-pk-card"),
                    card_code:$(obj).children().children().attr("data-card-code"),
                    staff_code:$(obj).children().children().attr("data-staff-code"),
                    be_return_cash:be_return_cash,
                    be_return_allowance:be_return_allowance,
                    be_return_deposit:be_return_deposit,
                    operator:JSON.parse(localStorage.user).pk_user,
                };
                params.push(param);
            });

            $.ajax({
                cache : true,
                type: 'post',
                url: getUrl()+'/Api/card/return',
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                data: JSON.stringify(params),
                success: function (data) {
                    if (data.success) {
                        Notify('退卡成功', 'top-right', '5000', 'primary', 'fa-times-circle', true);
                        returncard.refresh();
                    } else {
                        Notify('退卡失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                    }
                },
                error: function (data) {
                    Notify('退卡失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                }
            });

            $("#plcharge").modal('hide');

        });

        //批量退卡选择人员改变背景色
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
                        if ($(obj).children().children().attr("data-card-code") == $(obj1).children().children().attr("data-card-code")) {
                            flag = false;
                        }
                    });
                    if (flag) {
                        $("#right_psns ul").append("<li class='list-group-item'>" + $(obj).html() + "</li>");
                        $(obj).removeClass("selected");
                    } else {
                        return;
                    }
                }
            });
            $("#right_psns ul").children().removeClass("selected");
        });

        $("#move_right_all").click(function () {
            var leftitems = $("#left_psns ul");
            var right_psns = $("#right_psns ul li");
            var flag = true;
            $.each(leftitems.children(), function (idx, obj) {
                $.each(right_psns, function (idx1, obj1) {
                    if ($(obj).children().children().attr("data-card-code") == $(obj1).children().children().attr("data-card-code")) {
                        flag = false;
                    }
                });
            });

            if (flag) {
                $("#right_psns ul").append($(leftitems).html());
                $("#right_psns ul").children().removeClass("selected");
            } else {
                return;
            }

        });

        $("#move_left_one").click(function () {
            var rightitems = $("#right_psns ul li");
            $.each(rightitems, function (idx, obj) {
                if ($(obj).hasClass("selected")) {
                    $(obj).remove();
                }
            });
        });

        $("#move_left_all").click(function () {
            $("#right_psns ul").html("");
        });

    }

    returncard.refresh = function refresh() {
        var opt = {
            url:getUrl()+ "/Api/card/retrieve",
            silent: true,
            query: JSON.stringify({
                department_code: department_code,
                operator_type: "return",
                limit: 10,
                offset: 0,
                type: 1,
                level: 2
            })
        };

        $("#table").bootstrapTable('refresh', opt);
    }

    //1.初始化左侧树
    returncard.initTreeView();

    //2.初始化Table
    returncard.initTable();

    //3.初始化Button的点击事件
    returncard.initButton();

});
//部门树高度 背景色
(function(){
    setTimeout(function(){
        $('#tree').css({minHeight:deptTreeHeight,background:'#fff'});
    },500)
})();