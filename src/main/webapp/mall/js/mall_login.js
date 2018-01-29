var width_=$(window).width();
var height_=$(window).height();
$('#content').css({
    'width':width_+'px',
    'height':height_+'px'
});
$(function(){
	var loginVue=new Vue({
		el:"#content",
		data:{
			user:localStorage.user_code,
			password:localStorage.password
		},
		methods:{
			init:function(){

				if(localStorage.user_code=="" || localStorage.password==""){
				}else{
					$("#isSelect").prop('checked',true);
					$("#user").text(localStorage.user_code);
					$("#pwd").text(localStorage.password);
				}
			},
			login:function(){
				var user=$("#user").val();
    			var pwd=$("#pwd").val();
				localStorage.staff_code_login=$("#user").val();
    			if(user==""||pwd==""){        		
        			Notify('用户名或密码不能为空', 'top-right', '5000', 'danger', 'fa-times-circle', true);
   				 }else if(user!="" && pwd!=""){
					$("#sure").text("登录中...");
					$("#sure").attr("disabled",true);
   				 	$.ajax({
                    type:"POST",
                    url:getUrl()+"/Api/user/userlogin",
                    dataType:"JSON",
                    data:{
                    	user_code:user,
                    	password:pwd
                    },
                    success:function(data){
						$("#sure").text("登录");
						$("#sure").removeAttr("disabled");
                        if(data.success){
                        	if($("#isSelect").prop('checked')){
                        		localStorage.user_code=user;
                        		localStorage.password=pwd;
                        	}else{
                        		localStorage.user_code="";
                        		localStorage.password="";
                        	}
                         	Notify('登录成功', 'top-right', '5000', 'primary', 'fa-times-circle', true);   				
                       		localStorage.user_name=data.data[0].user_name;
                       		localStorage.pk_staff=data.data[0].pk_staff;
                       		window.location.href='index.html';
                        }else{
                        	Notify('用户名或密码错误', 'top-right', '5000', 'danger', 'fa-times-circle', true);   				
                        }
                    },
                    error:function(msg){
						Notify('网络连接失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
						console.log(msg);
                    }
                });
   				 }
			},
		}
	});
	loginVue.init();
});
