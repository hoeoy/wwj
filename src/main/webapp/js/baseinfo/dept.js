$(function (dept) {
    document.getElementById("tree").style.maxHeight=$(window).height()+'px';
    var department_code;
    var company_code;
    var id=parseInt(Math.random()*100);

    dept.initTreeView = function () {
        id=id+1;
        $.ajax({
            type: 'get',
            data: {
                company_code: "1",
                id:id
            },
            dataType: "json",
            url:getUrl()+ '/Api/dept/retrieveTree',
            contentType: "application/json;charset=UTF-8",
            success: function (data) {
                if (data.success) {
                    var nodes = JSON.stringify(listToTree(data.data));
                    $('#tree').treeview({
                        data: nodes,
                        onNodeSelected: function (event, node) {

                            $("#btn_edit").removeAttr('disabled');
                            $("#btn_delete").removeAttr('disabled');
                            $("#btn_add").removeAttr('disabled');

                            if(node.name == "dept"){
                                department_code = node.id;
                                company_code = getRootTreeNode("tree",node).id;
                                //刷新表格
                                dept.refresh();
                            }

                            if(node.name == "company"){
                                $("#btn_edit").attr("disabled","disabled");
                                $("#btn_delete").attr("disabled","disabled");
                                $("#department_code").val("");
                                $("#department_name").val("");
                                $("#memo").val("");
                                $("#pk_department").val("");
                                $("#parent_code").val("");
                                company_code = node.id;
                            }

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
    }

    function getRootTreeNode(treeId,node){
        var treeNode = $("#"+treeId).treeview('getParent', node);
        if(treeNode.treeNodeName == "company"){
            return treeNode;
        }else{
            return getRootTreeNode(treeId,treeNode);
        }
    }

    dept.initButton = function () {

        //新增
        $("#btn_add").click(function(){

            $("#btn_add").attr("disabled","disabled");
            $("#btn_edit").attr("disabled","disabled");
            $("#btn_delete").attr("disabled","disabled");
            $("#btn_save").removeAttr("disabled");
            $("#btn_cancel").removeAttr("disabled");

            $("input[name='department_info']").attr("readonly",false);

            $("#department_code").val("");
            $("#department_name").val("");
            $("#memo").val("");
            $("#pk_department").val("");
            $("#parent_code").val(department_code);

            $("#mymodal-data").modal('show');
        });

        //修改
        $("#btn_edit").click(function(){
            $("#btn_add").attr("disabled","disabled");
            $("#btn_edit").attr("disabled","disabled");
            $("#btn_delete").attr("disabled","disabled");
            $("#btn_save").removeAttr("disabled");
            $("#btn_cancel").removeAttr("disabled");
            $("input[name='department_info']").attr("readonly",false);
            $("#department_code").attr("readonly",true);

        });

        //删除
        $("#btn_delete").click(function(){

            if(confirm("您确定要删除吗？")){
                $.ajax({
                    type: 'post',
                    data:JSON.stringify([$("#pk_department").val()]),
                    dataType:"json",
                    url:getUrl()+ '/Api/dept/delete',
                    contentType: "application/json;charset=UTF-8",
                    success: function (data) {
                        if (data.success) {
                            Notify('删除成功', 'top-right', '5000', 'primary', 'fa-times-circle', true);
                            dept.initTreeView();
                        } else {
                            Notify('删除失败'+data.message, 'top-right', '5000', 'danger', 'fa-times-circle', true);

                        }
                    },
                    error: function (data) {
                        Notify('删除失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);

                    }
                });


            }



        });

        //取消
        $("#btn_cancel").click(function(){
            $("#btn_add").removeAttr("disabled");
            $("#btn_edit").removeAttr("disabled");
            $("#btn_delete").removeAttr("disabled");
            $("#btn_save").attr("disabled","disabled");
            $("#btn_cancel").attr("disabled","disabled");

            $("input[name='department_info']").attr("readonly",true);

            dept.refresh();

        });

        //保存
        $("#btn_save").click(function(){

            var department_code = $("#department_code").val();
            var department_name = $("#department_name").val();


            if(department_code == "" || department_name == ""){
                Notify('请填写完整信息', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                return;
            }

            var sendData = {
                department_code:department_code,
                department_name:department_name,
                company_code:company_code,
                memo:$("#memo").val(),
                //pk_department:$("#pk_department").val(),
                parent_code:$("#parent_code").val(),
            };
            sendData.pk_department = $("#pk_department").val() == "" ? null : $("#pk_department").val();
            $.ajax({
                type: 'post',
                data:JSON.stringify(sendData),
                dataType:"json",
                url:getUrl()+ '/Api/dept/save',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.success) {
                        Notify('保存成功', 'top-right', '5000', 'primary', 'fa-times-circle', true);
                        dept.initTreeView();
                        dept.refresh();

                    } else {
                        Notify('保存失败'+data.message, 'top-right', '5000', 'danger', 'fa-times-circle', true);
                    }
                },
                error: function (data) {
                    Notify('保存失败'+data.message, 'top-right', '5000', 'danger', 'fa-times-circle', true);
                }
            });
            pk_company = null;
        });



    }

    dept.refresh = function refresh() {
        console.log("refresh");
        $.ajax({
            type: 'post',
            data:JSON.stringify({department_code:department_code,company_code:company_code}),
            dataType:"json",
            url:getUrl()+ '/Api/dept/retrievedept',
            contentType: "application/json;charset=UTF-8",
            success: function (data) {
                if (data.success) {
                    $("#department_code").val(data.data.department_code);
                    $("#department_name").val(data.data.department_name);
                    $("#memo").val(data.data.memo);
                    $("#pk_department").val(data.data.pk_department);
                    $("#parent_code").val(data.data.parent_code);
                } else {
                    Notify('查询部门信息失败'+data.message, 'top-right', '5000', 'danger', 'fa-times-circle', true);
                }
            },
            error: function (data) {
                Notify('查询部门信息失败'+data.message, 'top-right', '5000', 'danger', 'fa-times-circle', true);
            }
        });
    }

    //1.初始化左侧树
    dept.initTreeView();

    //3.初始化Button的点击事件
    dept.initButton();

});
