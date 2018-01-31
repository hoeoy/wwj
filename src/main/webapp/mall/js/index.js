
function indexNavigate(url) {
    $("#content").load(url);
}


$(function(){
	Vue.filter("statu", function(value) {   //全局方法 Vue.filter() 注册一个自定义过滤器,必须放在Vue实例化前面
               if(value==3){
               	return '待支付';
               }else if(value==1){
               	return '已支付';
               }else if(value==2){
               	return '已取货';
               }
            });
    var gwcVue=new Vue({
        el:'#all_content',
        data:{
        	conurl:"",
        	noSearchData:false,
            user:'',//用户名
            password:'',//密码
            outClasses:[],//大类
            smallClasses:[],//小类
            pageNo:1,//商品页码
            allGoods:[],
            totals:'',//商品总数
            pages:'', //总页数
            currentPage:1,//当前页码
            Goods_id:'', //商品id
            Goods_name:'', //商品名称
            Goods_price:'', //价格
            Goods_img:'', //图片
            my_gwcs:[], //购物车
            mygwc_number:0, //购物车商品数量
            price_total_all:0,//购物车商品总价
            mygw:{
            	Goods_id:'', //商品id
            	Goods_name:'', //商品名称
           	 	Goods_price:'', //价格
            	Goods_img:'', //图片
            	Goods_numbers:'',
            	Goods_totals:'',
            	imgurl:''
            },//我的购物
            pages_totals:[],
			pages_totals2:[],
            pages_search_totals:[],
            pages_search_totals2:[],
            pages_class_totals:[],
            pages_class_totals2:[],
            sy:'sy',
            status:'',
            username:localStorage.user_name,
            chargeId:'',
            historyorders:[],
            check_Orders:[],
            Order_statu:'',
           
            
        },
        methods:{
            goTo:function(url){
                window.open(url,'_parent');
            },
            init:function(){
				console.log(localStorage.staff_code_login);
            	if(localStorage.user_name=="" || typeof(localStorage.user_name) == "undefined"){
					$("#zz").show();
					$(".nologin").show();
				}
				gwcVue.pages_totals.splice(0,gwcVue.pages_totals.length);
				gwcVue.pages_class_totals.splice(0,gwcVue.pages_class_totals.length);
                gwcVue.pages_search_totals.splice(0,gwcVue.pages_search_totals.length);
				gwcVue.outClasses.splice(0,gwcVue.outClasses.length);
				gwcVue.allGoods.splice(0,gwcVue.allGoods.length);
				
                $.ajax({
                    type:"POST",
                    url:getUrl()+"/Api/OrderStyle/findStyle",
                    dataType:"JSON",
                    success:function(data){
                        
                        for(var i=0;i<data.data.length;i++){
                            gwcVue.outClasses.push(data.data[i]);
                            
                        }
                    },
                    error:function(msg){
                        console.log(msg);
                    }
                });
				/*
                $.ajax({
                    type:"POST",
                    url:getUrl()+'/Api/Order/Find',
                    dataType:'json',
                    data:{
                    	pageNo:1
                    },
                    success:function(data){
                      
                        if(data.success){
                        	gwcVue.noSearchData=false;
                        	for(var j=0;j<data.data.list.length;j++){
                            gwcVue.allGoods.push(data.data.list[j]);
                            gwcVue.totals=data.data.total;
                            gwcVue.pages=data.data.pages;
                       	 }
                        }else{
                        	gwcVue.noSearchData=true;
                        }
                        gwcVue.pages_totals.splice(0,gwcVue.pages_totals.length);
                        gwcVue.pages_class_totals.splice(0,gwcVue.pages_class_totals.length);
                        gwcVue.pages_search_totals.splice(0,gwcVue.pages_search_totals.length);
                        if(gwcVue.pages>1){
                        	gwcVue.pages_totals.splice(0,gwcVue.pages_totals.length);
                    		gwcVue.pages_totals.push('首页','上一页','下一页','尾页');
                    		var myindex=1;
	                       	for(var i=0;i<gwcVue.pages;i++){
	                       		myindex=myindex+1;
	                       		gwcVue.pages_totals.splice(myindex,0,i+1);
	                       	}  
                        }else{}
                       		                
                        
                    },
                    error:function(msg){
						console.log(msg);
                    }
                });
                */

              if($("#fydh").children('a').outerText==1) {
              		$(this).parent().className='active_';
              	}

            },
            toPage:function(e){
            	var str=e.currentTarget.querySelector('a').outerText;	
            	if(str=="首页" || str=="上一页" || str=="下一页" || str=="尾页"){
            		if(str=="首页"){
            			$("#fydh").children('li').removeClass('active_');
	            		gwcVue.currentPage=1;
	            		var data_id=e.currentTarget.parentNode.children[2];          		
            			data_id.className='active_';          		
            		}else if(str=="尾页"){
            			$("#fydh").children('li').removeClass('active_');
            			gwcVue.currentPage=this.pages;
            			e.currentTarget.parentNode.children[this.pages+1].className='active_';
            		}else if(str=="下一页"){
            			
            			if(gwcVue.currentPage<gwcVue.pages){
            				$("#fydh").children('li').removeClass('active_');
            				gwcVue.currentPage=gwcVue.currentPage+1;
            				e.currentTarget.parentNode.children[gwcVue.currentPage+1].className='active_';
            			}else if(gwcVue.currentPage==gwcVue.pages){
            				
            			}
            		}else if(str=="上一页"){
            			
            			if(gwcVue.currentPage>1){
            				$("#fydh").children('li').removeClass('active_');
            				gwcVue.currentPage=gwcVue.currentPage-1;
            				e.currentTarget.parentNode.children[gwcVue.currentPage+1].className='active_';
            			
            			}else if(gwcVue.currentPage==1){
            				
            			}
            		}
            	}else{
            		$("#fydh").children('li').removeClass('active_');
            		e.currentTarget.className='active_';
            		gwcVue.currentPage=e.currentTarget.querySelector('a').outerText;
            	}
            	
            
            	$.ajax({
                    type:"POST",
                    url:getUrl()+'/Api/Order/Find',
                    dataType:'json',
                    data:{
                    	pageNo:gwcVue.currentPage
                    },
                    success:function(data){
                       
                        if(data.success){
                        	gwcVue.allGoods.splice(0,gwcVue.allGoods.length);
                        	gwcVue.noSearchData=false;
                        	for(var j=0;j<data.data.list.length;j++){
                            gwcVue.allGoods.push(data.data.list[j]);
                            gwcVue.totals=data.data.total;
                            gwcVue.pages=data.data.pages;
                       	 }
                        }else{
                        	gwcVue.noSearchData=true;
                        }
    
                    },
                    error:function(msg){
						console.log(msg);
                    }
                });
            },
            tosearchPage:function(e){
            	var str=e.currentTarget.querySelector('a').outerText;	
            	if(str=="首页" || str=="上一页" || str=="下一页" || str=="尾页"){
            		if(str=="首页"){
            			$("#fydh").children('li').removeClass('active_');
	            		gwcVue.currentPage=1;
	            		var data_id=e.currentTarget.parentNode.children[2];          		
            			data_id.className='active_';          		
            		}else if(str=="尾页"){
            			$("#fydh").children('li').removeClass('active_');
            			gwcVue.currentPage=this.pages;
            			e.currentTarget.parentNode.children[this.pages+1].className='active_';
            		}else if(str=="下一页"){
            			
            			if(gwcVue.currentPage<gwcVue.pages){
            				$("#fydh").children('li').removeClass('active_');
            				gwcVue.currentPage=gwcVue.currentPage+1;
            				e.currentTarget.parentNode.children[gwcVue.currentPage+1].className='active_';
            			}else if(gwcVue.currentPage==gwcVue.pages){
            				
            			}
            		}else if(str=="上一页"){
            			
            			if(gwcVue.currentPage>1){
            				$("#fydh").children('li').removeClass('active_');
            				gwcVue.currentPage=gwcVue.currentPage-1;
            				e.currentTarget.parentNode.children[gwcVue.currentPage+1].className='active_';
            			
            			}else if(gwcVue.currentPage==1){
            				
            			}
            		}
            	}else{
            		$("#fydh").children('li').removeClass('active_');
            		e.currentTarget.className='active_';
            		gwcVue.currentPage=e.currentTarget.querySelector('a').outerText;
            	}
            	
            	var goodsname=$("#search_text").val();
            	if(goodsname==""){}else{
            		gwcVue.allGoods.splice(0,gwcVue.allGoods.length);
            		$.ajax({
	                    type:"POST",
	                    url:getUrl()+'/Api/Order/Findgood',
	                    dataType:'json',
	                    data:{
	                    	goodsname:goodsname,
	                    	pageNo:gwcVue.currentPage
	                    },
	                    success:function(data){
	                        
	                        if(data.success){
	                        	gwcVue.noSearchData=false;
	                        	gwcVue.pages=data.data.pages;
	                        	
	                        	for(var i=0;i<data.data.list.length;i++){
	                        		gwcVue.allGoods.push(data.data.list[i]);
	                        	}
	                        }else{
	                        	gwcVue.noSearchData=true;
	                        }
	                    
	                      
	                    },
	                    error:function(msg){
	
	                    }
                });
            	}
            	
            },
            toclassPage:function(e){
            	var str=e.currentTarget.querySelector('a').outerText;	
            	if(str=="首页" || str=="上一页" || str=="下一页" || str=="尾页"){
            		if(str=="首页"){
            			$("#fydh").children('li').removeClass('active_');
	            		gwcVue.currentPage=1;
	            		var data_id=e.currentTarget.parentNode.children[2];          		
            			data_id.className='active_';          		
            		}else if(str=="尾页"){
            			$("#fydh").children('li').removeClass('active_');
            			gwcVue.currentPage=this.pages;
            			e.currentTarget.parentNode.children[this.pages+1].className='active_';
            		}else if(str=="下一页"){
            			
            			if(gwcVue.currentPage<gwcVue.pages){
            				$("#fydh").children('li').removeClass('active_');
            				gwcVue.currentPage=gwcVue.currentPage+1;
            				e.currentTarget.parentNode.children[gwcVue.currentPage+1].className='active_';
            			}else if(gwcVue.currentPage==gwcVue.pages){
            				
            			}
            		}else if(str=="上一页"){
            			
            			if(gwcVue.currentPage>1){
            				$("#fydh").children('li').removeClass('active_');
            				gwcVue.currentPage=gwcVue.currentPage-1;
            				e.currentTarget.parentNode.children[gwcVue.currentPage+1].className='active_';
            			
            			}else if(gwcVue.currentPage==1){
            				
            			}
            		}
            	}else{
            		$("#fydh").children('li').removeClass('active_');
            		e.currentTarget.className='active_';
            		gwcVue.currentPage=e.currentTarget.querySelector('a').outerText;
            	}
            	
            	var pk_only_id=e.currentTarget.querySelector('.my_smallid').outerText;
            	if(goodsname==""){}else{
            		gwcVue.allGoods.splice(0,gwcVue.allGoods.length);
            		$.ajax({
	                    type:"POST",
	                    url:getUrl()+'/Api/Order/Findtypegoods',
	                    dataType:'json',
	                    data:{
	                    	goodstype:pk_only_id,
	                    	pageNo:gwcVue.currentPage
	                    },
	                    success:function(data){
	                       
	                        if(data.success){
	                        	gwcVue.noSearchData=false;
	                        	gwcVue.pages=data.data.pages;
	                        	
	                        	for(var i=0;i<data.data.list.length;i++){
	                        		gwcVue.allGoods.push(data.data.list[i]);
	                        	}
	                        }else{
	                        	gwcVue.pages=1;
	                        	gwcVue.noSearchData=true;
	                        }
	                    
	                      
	                    },
	                    error:function(msg){
	
	                    }
                });
            	}
            	
            },
            showClass:function(e){
            	
            	gwcVue.smallClasses.splice(0,gwcVue.smallClasses.length);
                var pk_parent_id=e.currentTarget.querySelector('.my_id').outerText;
                	$.ajax({
	                    type:"POST",
	                    url:getUrl()+'/Api/OrderStyle/findtype',
	                    dataType:'json',
	                    data:{
	                    	pk_parentid:pk_parent_id
	                    },
	                    success:function(data){
	                        
	                        if(data.success){	
		                    	for(x=0;x<data.data.length;x++){                    	
		                    	gwcVue.smallClasses.push(data.data[x]);	                    	
		                   		}	
	                   		}else{
	                   		gwcVue.pages_totals.splice(0,gwcVue.pages_totals.length);
							gwcVue.pages_class_totals.splice(0,gwcVue.pages_class_totals.length);
			                gwcVue.pages_search_totals.splice(0,gwcVue.pages_search_totals.length);
							
	                   			gwcVue.allGoods.splice(0,gwcVue.allGoods.length);
	                   			gwcVue.noSearchData=true;
	                   		}
	                        
	                    
	                    },
	                    error:function(msg){
	
	                    }
                });
            	    
            },
            showSmallClass:function(e,index){
				$(".smallClasstree").find('li').removeClass('active_');
				e.currentTarget.className='active_';
            	gwcVue.allGoods.splice(0,gwcVue.allGoods.length);
            	gwcVue.pages_totals.splice(0,gwcVue.pages_totals.length);
            	gwcVue.pages_class_totals.splice(0,gwcVue.pages_class_totals.length);
                gwcVue.pages_search_totals.splice(0,gwcVue.pages_search_totals.length);
            	this.status = index;	
            	
            	var pk_only_id=e.currentTarget.querySelector('.my_smallid').outerText;
            
            	$.ajax({
	                    type:"POST",
	                    url:getUrl()+'/Api/Order/Findtypegoods',
	                    dataType:'json',
	                    data:{
	                    	goodstype:pk_only_id,
	                    	pageNo:1
	                    },
	                    success:function(data){
	                        
	                        if(data.success){
	                        	gwcVue.noSearchData=false;
	                        	gwcVue.pages=data.data.pages;
	                    		for(x=0;x<data.data.list.length;x++){                    	
	                    		gwcVue.allGoods.push(data.data.list[x]);	                    	
	                   		}
	                   			
	                   		}else{
	                   			gwcVue.pages=1;
	                   			gwcVue.noSearchData=true;
	                        }
	                    if(gwcVue.pages>1){
                        	gwcVue.pages_totals.splice(0,gwcVue.pages_totals.length);
                        	gwcVue.pages_class_totals.splice(0,gwcVue.pages_class_totals.length);
                        	gwcVue.pages_search_totals.splice(0,gwcVue.pages_search_totals.length);
                    		gwcVue.pages_search_totals.push('首页','上一页','下一页','尾页');
                    		var myindex=1;
	                       	for(var i=0;i<gwcVue.pages;i++){
	                       		myindex=myindex+1;
	                       		gwcVue.pages_search_totals.splice(myindex,0,i+1);
	                       	}  
                        }else{}    
	                   
	                    },
	                    error:function(msg){
	
	                    }
                });
            },
            submit:function(){
				$("#search_btn").val('搜索中....');
				$("#search_btn").attr('disabled',true);
				$("#content3").hide();
				$("#content2").hide();
				$("#content").show();
				$("#gwc_total").hide();
				$("#content4").hide();
				$("#content5").hide();
            	var goodsname=$("#search_text").val();
            	
            	if(goodsname==""){
            		
            	}else{
            	
            		gwcVue.pages_totals.splice(0,gwcVue.pages_totals.length);
            		gwcVue.pages_class_totals.splice(0,gwcVue.pages_class_totals.length);
                    gwcVue.pages_search_totals.splice(0,gwcVue.pages_search_totals.length);
            		gwcVue.allGoods.splice(0,gwcVue.allGoods.length);
            		$.ajax({
	                    type:"POST",
	                    url:getUrl()+'/Api/Order/Findgood',
	                    dataType:'json',
	                    data:{
	                    	goodsname:goodsname	                
	                    },
	                    success:function(data){
							$("#search_btn").val('搜索');
							$("#search_btn").removeAttr('disabled');
	                        if(data.success){
	                        	gwcVue.noSearchData=false;
	                        	gwcVue.pages=data.data.pages;
	                        	for(var i=0;i<data.data.list.length;i++){
	                        		gwcVue.allGoods.push(data.data.list[i]);
	                        	}
	                        }else{
	                        	gwcVue.pages=1;
	                        	gwcVue.noSearchData=true;
	                        	gwcVue.pages_totals.splice(0,gwcVue.pages_totals.length);
	                        	gwcVue.pages_class_totals.splice(0,gwcVue.pages_class_totals.length);
                        		gwcVue.pages_search_totals.splice(0,gwcVue.pages_search_totals.length);
                     
	                        }
	                        if(gwcVue.pages>1){
                        	gwcVue.pages_totals.splice(0,gwcVue.pages_totals.length);
                        	gwcVue.pages_class_totals.splice(0,gwcVue.pages_class_totals.length);
                        	gwcVue.pages_search_totals.splice(0,gwcVue.pages_search_totals.length);
                    		gwcVue.pages_search_totals.push('首页','上一页','下一页','尾页');
                    		var myindex=1;
	                       	for(var i=0;i<gwcVue.pages;i++){
	                       		myindex=myindex+1;
	                       		gwcVue.pages_search_totals.splice(myindex,0,i+1);
	                       	}  
                        }else{}
	                      
	                    },
	                    error:function(msg){
							$("#search_btn").val('搜索');
							$("#search_btn").removeAttr('disabled');
	                    }
                });
            	}
            },
        spxq:function(e){
        	var sp_id=e.currentTarget.querySelector('.sp_id').outerText;
        	 	$.ajax({
	                    type:"POST",
	                    url:getUrl()+'/Api/Order/Gooddetails',
	                    dataType:'json',
	                    data:{
	                    	id:sp_id	                
	                    },
	                    success:function(data){
	                        
	                        if(data.success){	                        	
	                    	 	gwcVue.Goods_id=sp_id;
	                    	 	gwcVue.Goods_name=data.data[0].goodsname;
	                    	 	gwcVue.Goods_price=data.data[0].price;
	                    	 	gwcVue.Goods_img=data.data[0].image;
	                    	 	$("#content").hide();
	                    	 	$("#content2").show();
	                    	 	$("#gwc_total").hide();
	                   			
	                   		}else{
	                   			
	                        }	                 
	                    },
	                    error:function(msg){
	
	                    }
                });
        
        },
        index:function(){
        	$("#content3").hide();
        	$("#content2").hide();
        	$("#content").show();
        	$("#gwc_total").hide();
        	$("#content4").hide();
        	$("#content5").hide();
        },
        add_number:function(){
        	var number=parseInt($("#sp_number").val());
		    number=number+1;
		    
		    document.getElementById("sp_number").value=number;
        },
        jian_number:function(){
        	var number=parseInt($("#sp_number").val());
		    number=number-1;
		    
		    if(number<1){
		
		    }else{
		        document.getElementById("sp_number").value=number;
		        }
       },
        add_goods:function(e){
        	var dj=e.currentTarget.parentNode.parentNode.parentNode.querySelector('.dj').outerText;
        	dj=parseInt(dj.slice(1,dj.length));
        	var number=parseInt(e.currentTarget.parentNode.querySelector('.buy_numbers').value);
		    number=number+1;
		    
		    e.currentTarget.parentNode.querySelector('.buy_numbers').value=number;
		    var totals=number*dj;
		   
		  	e.currentTarget.parentNode.parentNode.parentNode.querySelector('.content3_total_prize').innerText="￥"+totals; 
		    this.total_price(dj);
        },
         jian_goods:function(e){
         	var dj=e.currentTarget.parentNode.parentNode.parentNode.querySelector('.dj').outerText;
        	dj=parseInt(dj.slice(1,dj.length));
        	
        	var number=parseInt(e.currentTarget.parentNode.querySelector('.buy_numbers').value);
		    number=number-1;
		    
		    if(number<1){
		
		    }else{
		        e.currentTarget.parentNode.querySelector('.buy_numbers').value=number;
		         var totals=number*dj;
		    	e.currentTarget.parentNode.parentNode.parentNode.querySelector('.content3_total_prize').innerText="￥"+totals; 
		   		/*gwcVue.price_total= parseInt(gwcVue.price_total)-dj;*/
		    }
       },
		addGwc:function(){
			var sp_name=$("#content2_title").text();
			var sp_price=$("#content2_peice").text();
			var price=sp_price.slice(1,sp_price.length);
			var sp_numnersss=parseInt($("#sp_number").val());			
			gwcVue.mygwc_number=gwcVue.mygwc_number+sp_numnersss;
			var totals=price*sp_numnersss;
			gwcVue.price_total_all=gwcVue.price_total_all+totals;
			var goodsimg=$("#mychoicegoodimg").attr('src');
			
			Notify('加入购物车成功', 'top-right', '5000', 'primary', 'fa-check-circle', true);
			gwcVue.my_gwcs.push({
				Foodname:sp_name,
				Price:price,
				Count:sp_numnersss,
				goodstotals:totals,
				goodsimg:goodsimg
			})
			},
			addGw:function(){
				var sp_name=$("#content2_title").text();
				var sp_price=$("#content2_peice").text();
				var price=sp_price.slice(1,sp_price.length);
				price=trimStr(price);
				var sp_numnersss=parseInt($("#sp_number").val());
				var totals=price*sp_numnersss;
				var imgurl=$("#mychoicegoodimg").attr('src');
				gwcVue.mygw.Goods_name=sp_name;
				gwcVue.mygw.Goods_price=price;
				gwcVue.mygw.Goods_numbers=sp_numnersss;
				gwcVue.mygw.Goods_totals=totals;
				gwcVue.mygw.imgurl=imgurl;
				$("#content3").hide();
	        	$("#content2").hide();
	        	$("#content").hide();
	        	$("#gwc_total").hide();
	        	$("#content4").show();
	        	$("#content5").hide();
				
			},
			toGwc:function(){
				$("#content").hide();
				$("#content2").hide();
				$("#content3").show();
				$("#gwc_total").show();
				$("#content4").hide();
				$("#content5").hide();
			},
			delete_gwc_list:function(index){
				
				gwcVue.mygwc_number=gwcVue.mygwc_number-gwcVue.my_gwcs[index].goodsnumbers;
				gwcVue.price_total_all=gwcVue.price_total_all-gwcVue.my_gwcs[index].goodstotals;
				this.my_gwcs.splice(index,1);
				tips("删除成功！");
				
			
			},
			total_price:function(dj){
		     gwcVue.price_total_all= parseInt(this.price_total_all)+dj;
			},
			clear_gwc:function(){
				gwcVue.my_gwcs.splice(0,gwcVue.my_gwcs.length);
				gwcVue.price_total_all=0;
				gwcVue.mygwc_number=0;
				tips("清空成功！");
				
			},
			update:function(){
				$("#updatePasswordModal").modal('show');
			},
			updateNewPwd:function(){
				
				var oldpwd=$("#oldpwd").val();
				var newpwd=$("#newpwd").val();
				var newpwd2=$("#newpwd2").val();
			
				if(oldpwd=="" || newpwd=="" ||newpwd2==""){
					$("#dl_error3").text('密码不能为空');
				}
				else if(newpwd!=newpwd2){
					$("#dl_error3").text('两次密码输入不一致');
					
				}else if(oldpwd!=""&& newpwd==newpwd2){
					$.ajax({
	                    type:"POST",
	                    url:getUrl()+'/Api/user/updatePwd',
	                    dataType:'json',
	                    data:{
	                    	user_code:localStorage.staff_code_login,
	                    	Oldpwd:oldpwd,
	                    	Password:newpwd
	                    },
	                    success:function(data){
	                        if(data.message){	                        	
	                    	 	Notify('修改成功,即将返回登录页面', 'top-right', '5000', 'primary', 'fa-check-circle', true);
								$("#updatePasswordModal").modal('hide');
	                   			$("#dl_error3").text('');
								window.location.href="mall_login.html";
	                   		}else{
	                   			$("#dl_error3").text(data.message);
	                        }	                 
	                    },
	                    error:function(msg){
	
	                    }
                });
				}
			},
			updateCharge:function(){
				$("#updateChargePasswordModal").modal('show');
			},
			updateChargePwd:function(){
				var newchargepwd=$("#newchargepwd").val();				
				if(newchargepwd==""){
					$("#dl_error4").text('密码不能为空');
				}else{
					$.ajax({
	                    type:"POST",
	                    url:getUrl()+'/Api/card/updatePwd',
	                    dataType:'json',
	                    data:{	                    	
	                    	pk_staff:localStorage.pk_staff,
	                    	Password:newchargepwd
	                    },
	                    success:function(data){
	                        if(data.success){	                        	
	                    	 	Notify('修改成功', 'top-right', '5000', 'primary', 'fa-check-circle', true);
								$("#updateChargePasswordModal").modal('hide');	                   
	                   			$("#dl_error4").text('');
	                   		}else{
	                   			Notify('修改失败', 'top-right', '5000', 'danger', 'fa-check-circle', true);								
	                        }	                 
	                    },
	                    error:function(msg){
	
	                    }
                });
				}
			},
			chargepwd:function(){
				this.chargeId=0;
				$("#charge_pwd").show();
				$("#zz").show();
				
			},
        	chargemiss:function(){

        		$("#charge_pwd").hide();
        		$("#zz").hide();
        		
        	},
        	chargepwd2:function(){
                $("#charge_pwd_text").val("");
        		this.chargeId=1;
				$("#charge_pwd").show();
				$("#zz").show();
				
			},
        	chargemiss2:function(){
        		$("#charge_pwd").hide();
        		$("#zz").hide();
        		
        	},
        	chargeOnline:function(){

        		var chargePwd=$("#charge_pwd_text").val();
        		if(chargePwd==""){
        				Notify('密码不能为空', 'top-right', '5000', 'danger', 'fa-check-circle', true);
        		}else{
        			if(this.chargeId==0){
        			var Lists=[];
        			for(var i=0;i<gwcVue.my_gwcs.length;i++){
        			var List={
        				foodname:gwcVue.my_gwcs[i].Foodname,
        				price:gwcVue.my_gwcs[i].Price,
        				count:gwcVue.my_gwcs[i].Count,
        				staff_code:localStorage.pk_staff,
        				passwrod:chargePwd,
        				formpeople:localStorage.user_name	,
        				meal_money:gwcVue.price_total_all
        			}
        			Lists.push(List);
        		}

        		$.ajax({
        				cache:true,
	                    type:"POST",
	                    url:getUrl()+'/Api/OrderForm/insert',
	                    dataType:'json',
	                    contentType:'application/json;charset=UTF-8',
	                    data:JSON.stringify(Lists),
	                    success:function(data){
							console.log(data);
	                        if(data.success){
	                        	gwcVue.my_gwcs.splice(0,gwcVue.my_gwcs.length);
	                        	gwcVue.price_total_all=0;
	                        	gwcVue.mygwc_number=0;	                        	
	                        	Notify('提交成功', 'top-right', '5000', 'primary', 'fa-check-circle', true);								
                      			$("#charge_pwd").hide();
        						$("#zz").hide();
	                        }else{
	                         	Notify('提交失败,'+data.message, 'top-right', '5000', 'danger', 'fa-check-circle', true);
                      	
	                        }
	                                   
	                    },
	                    error:function(msg){
	
	                    }
                });
        		}else if(this.chargeId==1){
        			var Lists2=[];        			
        			Lists2.push({
        				foodname:gwcVue.mygw.Goods_name,
						price:gwcVue.mygw.Goods_price,
						count:gwcVue.mygw.Goods_numbers,
						staff_code:localStorage.pk_staff,
        				passwrod:chargePwd,
        				formpeople:localStorage.user_name,
        				meal_money:gwcVue.mygw.Goods_totals
        			});
					console.log(Lists2);
        			$.ajax({
        				cache:true,
	                    type:"POST",
	                    url:getUrl()+'/Api/OrderForm/insert',
	                    dataType:'json',
	                    contentType:'application/json;charset=UTF-8',
	                    data:JSON.stringify(Lists2),
	                    success:function(data){

                            console.log(data);



							console.log(data);

	                        if(data.success){
								console.log(data);
	                        	$("#content4").hide();
	                        	$("#content").show();
	                        	Notify('提交成功', 'top-right', '5000', 'primary', 'fa-check-circle', true);								
                      			$("#charge_pwd").hide();
        						$("#zz").hide();
	                        }else{
	                         	Notify('提交失败,'+data.message, 'top-right', '5000', 'danger', 'fa-check-circle', true);
                      	
	                        }
	                                   
	                    },
	                    error:function(msg){
	
	                    }
                });
        		}
        			
        		}
        		
        		
        	},
        	inlineCharge:function(id){
        		if(id==0){
        			if(gwcVue.my_gwcs.length==0){
        				
        			}else{
        				var Lists=[];
        			for(var i=0;i<gwcVue.my_gwcs.length;i++){
        			var List={
        				foodname:gwcVue.my_gwcs[i].Foodname,
        				price:gwcVue.my_gwcs[i].Price,
        				count:gwcVue.my_gwcs[i].Count,
        				staff_code:localStorage.pk_staff,
        				passwrod:'',
        				formpeople:localStorage.user_name,
        				meal_money:gwcVue.price_total_all
        			}
        			Lists.push(List);
	        		}

	        		$.ajax({
	        				cache:true,
		                    type:"POST",
		                    url:getUrl()+'/Api/OrderForm/insert2',
		                    dataType:'json',
		                    contentType:'application/json;charset=UTF-8',
		                    data:JSON.stringify(Lists),
		                    success:function(data){

		                        if(data.success){
		                        	gwcVue.my_gwcs.splice(0,gwcVue.my_gwcs.length);
		                        	gwcVue.price_total_all=0;
		                        	gwcVue.mygwc_number=0;	                        	
		                        	Notify('下单成功', 'top-right', '5000', 'primary', 'fa-check-circle', true);								
	                      			 }else{
		                         	Notify('下单失败', 'top-right', '5000', 'danger', 'fa-check-circle', true);								
	                      	
		                        }
		                                   
		                    },
		                    error:function(msg){
		
		                    }
	                });
        			}
        			
        		}else if(id==1){
        			var Lists2=[];        			
        			Lists2.push({
        				foodname:gwcVue.mygw.Goods_name,
						price:gwcVue.mygw.Goods_price,
						count:gwcVue.mygw.Goods_numbers,
						staff_code:localStorage.pk_staff,
        				passwrod:'',
        				formpeople:localStorage.user_name,
        				meal_money:gwcVue.mygw.Goods_totals
        			});
        			
        			$.ajax({
        				cache:true,
	                    type:"POST",
	                    url:getUrl()+'/Api/OrderForm/insert2',
	                    dataType:'json',
	                    contentType:'application/json;charset=UTF-8',
	                    data:JSON.stringify(Lists2),
	                    success:function(data){
	                       
	                        if(data.success){
	                        	$("#content4").hide();
	                        	$("#content").show();
	                        	Notify('下单成功', 'top-right', '5000', 'primary', 'fa-check-circle', true);								
                      			
	                        }else{
	                         	Notify('下单失败', 'top-right', '5000', 'danger', 'fa-check-circle', true);								
                      	
	                        }
	                                   
	                    },
	                    error:function(msg){
	
	                    }
                });
        		}
        	},
			toHistory:function(e){
				var str=e.currentTarget.querySelector('a').outerText;
				if(str=="首页" || str=="上一页" || str=="下一页" || str=="尾页"){
					if(str=="首页"){
						$("#fydh2").children('li').removeClass('active_');
						gwcVue.currentPage=1;
						var data_id=e.currentTarget.parentNode.children[2];
						data_id.className='active_';
					}else if(str=="尾页"){
						$("#fydh2").children('li').removeClass('active_');
						gwcVue.currentPage=this.pages;
						e.currentTarget.parentNode.children[this.pages+1].className='active_';
					}else if(str=="下一页"){

						if(gwcVue.currentPage<gwcVue.pages){
							$("#fydh2").children('li').removeClass('active_');
							gwcVue.currentPage=gwcVue.currentPage+1;
							e.currentTarget.parentNode.children[gwcVue.currentPage+1].className='active_';
						}else if(gwcVue.currentPage==gwcVue.pages){

						}
					}else if(str=="上一页"){
						if(gwcVue.currentPage>1){
							$("#fydh2").children('li').removeClass('active_');
							gwcVue.currentPage=gwcVue.currentPage-1;
							e.currentTarget.parentNode.children[gwcVue.currentPage+1].className='active_';
						}else if(gwcVue.currentPage==1){

						}
					}
				}else{
					$("#fydh2").children('li').removeClass('active_');
					e.currentTarget.className='active_';
					gwcVue.currentPage=e.currentTarget.querySelector('a').outerText;
				}


				$.ajax({
					type:"POST",
					url:getUrl()+'/Api/OrderForm/findformforname',
					dataType:'json',
					data:{
						staff_code:localStorage.pk_staff,
						pageNo:gwcVue.currentPage
					},
					success:function(data){
						if(data.success){
							gwcVue.historyorders.splice(0,gwcVue.historyorders.length);
							for(var j=0;j<data.data.list.length;j++){
								gwcVue.historyorders.push(data.data.list[j]);
							}
						}else{
						}

					},
					error:function(msg){
						console.log(msg);
					}
				});
			},
        	history_orders:function(){
        		gwcVue.historyorders.splice(0,gwcVue.historyorders.length);
        		$("#content3").hide();
	        	$("#content2").hide();
	        	$("#content").hide();
	        	$("#gwc_total").hide();
	        	$("#content4").hide();
	        	$("#content5").show();
        		$.ajax({
        				cache:true,
	                    type:"POST",
	                    url:getUrl()+'/Api/OrderForm/findformforname',
	                    dataType:'json',	                   
	                    data:{
	                    	staff_code:localStorage.pk_staff,
							pageNo:gwcVue.currentPage
	                    },
	                    success:function(data){
							console.log(data);
	                        if(data.success){
								gwcVue.pages=data.data.pages;
	                        	for(var i=0;i<data.data.list.length;i++){
	                        		gwcVue.historyorders.push(data.data.list[i]);
	                        	}
	                        }else{
								gwcVue.pages=1;
								gwcVue.pages_search_totals2.splice(0,gwcVue.pages_search_totals2.length);
								gwcVue.pages_search_totals2.push('首页','上一页','1','下一页','尾页');
	                        }
							if(gwcVue.pages>1){
								gwcVue.pages_totals2.splice(0,gwcVue.pages_totals2.length);
								gwcVue.pages_search_totals2.splice(0,gwcVue.pages_search_totals2.length);
								gwcVue.pages_search_totals2.push('首页','上一页','下一页','尾页');
								var myindex=1;
								for(var i=0;i<gwcVue.pages;i++){
									myindex=myindex+1;
									gwcVue.pages_search_totals2.splice(myindex,0,i+1);
								}
							}else{
								gwcVue.pages_totals2.splice(0,gwcVue.pages_totals2.length);
								gwcVue.pages_search_totals2.splice(0,gwcVue.pages_search_totals2.length);
								gwcVue.pages_search_totals2.push('首页','上一页','1','下一页','尾页');
							}
	                    },
	                    error:function(msg){
	
	                    }
                });
        		
	        	
        	},
        	checkOrder:function(e){
        		gwcVue.check_Orders.splice(0,gwcVue.check_Orders.length);
        		var pk_formcode=e.currentTarget.outerText;

        		$.ajax({
        				cache:true,
	                    type:"POST",
	                    url:getUrl()+'/Api/OrderDetail/findOrderDetail',
	                    dataType:'json',
	                   
	                    data:{
	                    	fk_formcode:pk_formcode
	                    },
	                    success:function(data){

	                        if(data.success){
	                        	$("#OrderContentModal").modal('show');
	                        for(var i=0;i<data.data.length;i++){
	                        		gwcVue.check_Orders.push(data.data[i]);
	                        	}
	                        gwcVue.Order_statu=gwcVue.check_Orders[0].formstatus;
	                        }else{
	                       	
	                        }
	                                   
	                    },
	                    error:function(msg){
	
	                    }
                });
        	},
        }
        
    });
    gwcVue.init();
	
});


function tips(str){	
	$("#tips").html(str);
	$("#tips").show();
	setTimeout(function(){
		$("#tips").hide();
	},2000);
}

   function trimStr(str){
     	return str.replace(/(^\s*)|(\s*$)/g,"");
     }