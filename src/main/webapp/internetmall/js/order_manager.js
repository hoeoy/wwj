$(function(){
		Vue.filter("statu", function(value) {   //全局方法 Vue.filter() 注册一个自定义过滤器,必须放在Vue实例化前面
               if(value==3){
               	return '未支付';
               }else if(value==1){
               	return '已支付';
               }else if(value==2){
               	return '已取货';
               }
            });
	var orderVue=new Vue({
		el:"#all_content",
		data:{
			order_lists:[],
			check_Orders:[],
			Order_statu:'',
			Order_people:'',
			Order_amount:'',
			pk_formcode:'',
			cardNumber:'',
			pageNo:1,
			currentPage:1,
			nextPage:2,
			pages:'',
			pages_totals:['<',">"]
		},
		methods:{
			init:function(){
				laydate({
			  elem: '#start_time', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			//  event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			});
			laydate({
			  elem: '#end_time', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			//  event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			});
			},
			
			Search:function(){
				orderVue.order_lists.splice(0,orderVue.order_lists.length);
				orderVue.pages_totals.splice(0,orderVue.pages_totals.length);
				orderVue.pages_totals.push('<','>');
				var starttime=$("#start_time").val();
				var endtimes;
				var cardId=$("#codeId").val();
				var status=$("#select_Statu").val();
				if($("#end_time").val()==""){
					endtimes="";
				}else{
					var t1=parseInt($("#end_time").val().substr($("#end_time").val().length-2,2))+1;
					if(t1<10){
						t1='0'+t1;
					}
					var t2=$("#end_time").val().substr(0,$("#end_time").val().length-2);
					endtimes=t2+t1;
				}
				if(status==-1){
					Notify('请选择要查询的订单状态', 'top-right', '5000', 'danger', 'fa-times-circle', true);   			
				}else{
					$.ajax({
                    type:"POST",
                    url:getUrl()+"/Api/OrderForm/findformforname",
                    dataType:"JSON",
                    data:{
                    	formstatus:status,
                    	card_code:cardId,
                    	formtime:starttime,
                    	stopTime:endtimes,
                    	pageNo:orderVue.pageNo
                    },
                    success:function(data){
						console.log(data);
                        if(data.success){
                        	for(var i=0;i<data.data.list.length;i++){
                        		orderVue.order_lists.push(data.data.list[i]);
                        	}
							var pageindex=1;
							orderVue.pages=data.data.pages;
							orderVue.currentPage=data.data.pageNum;
							orderVue.nextPage=data.data.nextPage;
							for(var j=0;j<data.data.pages;j++){
								orderVue.pages_totals.splice(pageindex,0,j+1);
								pageindex++;
							}
                        }else{
                         }
                        
                    },
                    error:function(msg){
                        console.log(msg);
                    }
                });
				}
			},
			checkOrder:function(e){
				orderVue.check_Orders.splice(0,orderVue.check_Orders.length);
        		var pk_formcode=e.currentTarget.outerText;
        		this.pk_formcode=pk_formcode;
        		this.Order_people=e.currentTarget.parentNode.parentNode.querySelector('.formpeople').outerText;
        		this.Order_amount=e.currentTarget.parentNode.parentNode.querySelector('.amount').outerText;

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
	                        		orderVue.check_Orders.push(data.data[i]);
	                        	}
	                        orderVue.Order_statu=orderVue.check_Orders[0].formstatus;
	                        }else{
	                       	
	                        }
	                                   
	                    },
	                    error:function(msg){
	
	                    }
                });
			},
			chargeAndget:function(){
				var r=confirm('确认支付并取货！');
				if(r==true){
					$.ajax({
        				cache:true,
	                    type:"POST",
	                    url:getUrl()+'/Api/OrderForm/updateForm',
	                    dataType:'json',
	                   
	                    data:{
	                    	pk_formcode:this.pk_formcode,
	                    	staff_code:localStorage.pk_staff,
	                    	meal_money:this.Order_amount,
                            pk_device:JSON.parse(localStorage.user).pk_device
	                    },
	                    success:function(data){

	                        if(data.success){
	                        	Notify('订单取货成功！','top-right','5000', 'primary', 'fa-times-circle', true);	
	                       		$("#OrderContentModal").modal('hide');
	                       		orderVue.Search();
	                        }else{
	                       		Notify('失败！','top-right','5000', 'danger', 'fa-times-circle', true);	
	                       
	                        }
	                                   
	                    },
	                    error:function(msg){
	
	                    }
                });
				}else{
					
				}
				
			},
			GetGood:function(){
				$.ajax({
        				cache:true,
	                    type:"POST",
	                    url:getUrl()+'/Api/OrderForm/updateForm',
	                    dataType:'json',
	                   
	                    data:{
	                    	pk_formcode:this.pk_formcode,
	                    	
	                    },
	                    success:function(data){

	                        if(data.success){
	                        		Notify('取货成功！','top-right','5000', 'primary', 'fa-times-circle', true);	
	                       			$("#OrderContentModal").modal('hide'); 
	                       			orderVue.Search();
	                        }else{
	                       			Notify('取货失败！','top-right','5000', 'danger', 'fa-times-circle', true);	
	                       	  
	                        }
	                                   
	                    },
	                    error:function(msg){
	
	                    }
                });
			},
			readCard:function(){
				console.log('读卡');
				var v = AxRead.RequestPhyNo( ) ;// 调取硬件读取卡号
				if( v == 0 ) alert("没有检测到卡或者发卡器");
				else this.cardNumber = v;
				this.Search();
			},
			toPage:function(e){
				var str=e.currentTarget.querySelector('a').outerText;
				if(str=="<" || str==">"){
					 if(str==">"){
						if(orderVue.currentPage<orderVue.pages){
							$("#fydh").children('li').removeClass('active_');
							orderVue.currentPage=orderVue.nextPage;
							e.currentTarget.parentNode.children[orderVue.nextPage].className='active_';
						}else if(orderVue.currentPage==orderVue.pages){
							Notify('已经是最后一页了', 'top-right', '5000','warning', 'fa-warning', true);
						}
					}else if(str=="<"){
						if(orderVue.currentPage>1){
							$("#fydh").children('li').removeClass('active_');
							orderVue.currentPage=orderVue.currentPage-1;
							e.currentTarget.parentNode.children[orderVue.currentPage+1].className='active_';

						}else if(orderVue.currentPage==1){
							Notify('已经是第一页了', 'top-right', '5000', 'warning', 'fa-warning', true);
						}
					}
				}else{
					$("#fydh").children('li').removeClass('active_');
					e.currentTarget.className='active_';
					orderVue.currentPage=e.currentTarget.querySelector('a').outerText;
				}
				orderVue.order_lists.splice(0,orderVue.order_lists.length);
				var starttime=$("#start_time").val();
				var endtimes;
				var cardId=$("#cardId").val();
				var status=$("#select_Statu").val();
				if($("#end_time").val()==""){
					endtimes="";
				}else{
					var t1=parseInt($("#end_time").val().substr($("#end_time").val().length-2,2))+1;
					if(t1<10){
						t1='0'+t1;
					}
					var t2=$("#end_time").val().substr(0,$("#end_time").val().length-2);
					endtimes=t2+t1;
				}
				$.ajax({
					type:"POST",
					url:getUrl()+"/Api/OrderForm/findformforname",
					dataType:"JSON",
					data:{
						formstatus:status,
                        card_code:cardId,
						formtime:starttime,
						stopTime:endtimes,
						pageNo:orderVue.currentPage
					},
					success:function(data){
						console.log(data);
						if(data.success){
							orderVue.pages=data.data.pages;
							orderVue.currentPage=data.data.pageNum;
							orderVue.nextPage=data.data.nextPage;
							for(var i=0;i<data.data.list.length;i++){
								orderVue.order_lists.push(data.data.list[i]);
							}

						}else{
						}

					},
					error:function(msg){
						console.log(msg);
					}
				});
			}
		}
	});
	orderVue.init();
});
