$(function(){
	var styleVue=new Vue({
		el:"#all_content_style",
		data:{
			outClasses:[],//所有大类
			smallClasses:[],//小类
			parentClass:'',
			nodata:false,
			choiceNumbers:0,
			type:0,//类别：0为未选择  1为大类  2为小类
			myChecked_small:[], //我的小类选中
			myChecked_small2:[],
			myChecked_big:[], //我的大类选中
			pk_parentid:'',
			choice_numbers:0,
			
			
		},
		methods:{
			init:function(){
				styleVue.outClasses.splice(0,styleVue.outClasses.length);
				 $.ajax({
                    type:"POST",
                    url:getUrl()+"/Api/OrderStyle/findStyle",
                    dataType:"JSON",
                    success:function(data){
                        for(var i=0;i<data.data.length;i++){
                            styleVue.outClasses.push(data.data[i]);   
                        }
                        
                    },
                    error:function(msg){
                        console.log(msg);
                    }
                });
			},			
			choiceClass:function(e){
				$("#checkAll").prop('checked',false);
				styleVue.myChecked_big.splice(0,styleVue.myChecked_big.length);
				styleVue.myChecked_small.splice(0,styleVue.myChecked_small.length);
				$("#add").removeAttr('disabled');
				this.choice_numbers=0;
				this.choiceNumbers=0;
				$("#delete").attr('disabled',true);
				$("#update").attr('disabled',true);
				$("#treeview-menu2").children('li').removeClass('active2');
				e.currentTarget.className='active2';
				var id=e.currentTarget.querySelector('.my_smallid').outerText;
				this.pk_parentid=id;
				styleVue.parentClass=trimStr(e.currentTarget.querySelector('.menu_title').outerText);
				styleVue.smallClasses.splice(0,styleVue.smallClasses.length);
				if(id==0){
					this.type=1;
					$.ajax({
                    type:"POST",
                    url:getUrl()+"/Api/OrderStyle/findStyle",
                    dataType:"JSON",
                    success:function(data){
                      
                        for(var i=0;i<data.data.length;i++){
                            styleVue.smallClasses.push({
                            	pk_only:data.data[i].id,
                            	typename:data.data[i].stylename
                            	
                            });
                            
                        }
                        
                    },
                    error:function(msg){
                        console.log(msg);
                    }
                });
				}else{
					this.type=2;
					$.ajax({
	                    type:"POST",
	                    url:getUrl()+'/Api/OrderStyle/findtype',
	                    dataType:'json',
	                    data:{
	                    	pk_parentid:id
	                    },
	                    success:function(data){
	                        
	                        if(data.success){	
	                        	this.nodata=false;
		                    	for(x=0;x<data.data.length;x++){                    	
		                    	styleVue.smallClasses.push(data.data[x]);	                    	
		                   		}	
	                   		}else{
	                   			this.nodata=true;
	                   		}
	               
	                    },
	                    error:function(msg){
	
	                    }
                	});
				}
				
			},
			checked:function(e){
				var pk_only=e.currentTarget.parentNode.parentNode.querySelector('.pk_only').outerText;
				var name=e.currentTarget.parentNode.parentNode.querySelector('.typename').outerText;				
				var check=document.getElementsByName('checked');
				if(e.currentTarget.checked){	
					this.choice_numbers+=1;
					
					this.choiceNumbers+=1;	
					if(this.type==1){
						this.myChecked_big.push({
							id:pk_only,
							name:name
						});

					}else if(this.type==2){
						this.myChecked_small.push(pk_only);
						this.myChecked_small2.push(name);

					}
				}else{
					this.choice_numbers-=1;
					this.choiceNumbers-=1;
					
					 $("#checkAll").prop('checked',false);
					 if(this.type==1){
					 	 for(var i=0; i<this.myChecked_big.length; i++) {
							    if(this.myChecked_big[i].id == pk_only) {
							      this.myChecked_big.splice(i, 1);
							     
							      break;
							    }
							  }
					 }else if(this.type==2){
					 	for(var i=0; i<this.myChecked_small.length; i++) {
							    if(this.myChecked_small[i] == pk_only) {
							      this.myChecked_small.splice(i, 1);
							     
							      break;
							    }
							  }
					 	for(var j=0; j<this.myChecked_small2.length; j++) {
							    if(this.myChecked_small2[i] == name) {
							      this.myChecked_small2.splice(i, 1);
							      
							      break;
							    }
							  }
					 }
					
					 
				}
				if(this.choice_numbers>0){
						$("#delete").removeAttr('disabled');
					if(this.choice_numbers==1){
						$("#update").removeAttr('disabled');
					}else{
						$("#update").attr('disabled',true);
					}
				}else {
					$("#delete").attr('disabled',true);
					$("#update").attr('disabled',true);
				}
				if(check.length==this.choiceNumbers){
					$("#checkAll").prop('checked',true);
				}
			},
			addClass:function(){
				if(this.type==0){
					Notify('请选择一个类别', 'top-right', '5000', 'danger', 'fa-times-circle', true); 
				}else if(this.type==1){
					$("#addbigClassmodal").modal('show');
				}else if(this.type==2){
					$("#addsmallClassmodal").modal('show');
				}
			},
			deleteClass:function(){
				if(this.type==1){
					var length=styleVue.myChecked_big.length;
					
					if(length>1){
						alert("每次只能删除一个大类！");
					}else{
						$.ajax({
	                    type:"POST",
	                    url:getUrl()+'/Api/OrderStyle/findtype',
	                    dataType:'json',
	                    data:{
	                    	pk_parentid:styleVue.myChecked_big[0].id
	                    },
	                    success:function(data){
	                       
	                        if(data.success){	  	                        	
	                        	styleVue.deleteBClass_Failed(styleVue.myChecked_big[0].name);	
	                   		}else{
	                   			styleVue.deleteBClass();
	                   		}
	               
	                    },
	                    error:function(msg){
	
	                    	}
                		});
					}
				
					//alert(1);
					//
				}else if(this.type==2){
					var length2=styleVue.myChecked_small.length;
					for(var j=0;j<length2;j++){
						
						$.ajax({
	                    type:"POST",
	                    url:getUrl()+'/Api/OrderStyle/delOrderStype',
	                    dataType:'json',
	                    data:{
	                    	pk_only:styleVue.myChecked_small[j]
	                    },
	                    success:function(data){
	                       
	                        if(data.success){
	                        	this.choice_numbers=0;
	                        	Notify('删除成功！', 'top-right', '5000', 'primary', 'fa-circle-o', true); 
								styleVue.smallClasses.splice(0,styleVue.smallClasses.length);
								styleVue.refreshSmallClass(2);
	                   		}else{
	                   			Notify('删除失败！', 'top-right', '5000', 'danger', 'fa-circle-o', true); 
								
	                   		}
	               
	                    },
	                    error:function(msg){
	
	                    	}
                		});
					}
				
				}
			},
			deleteBClass:function(){								
						$.ajax({
	                    type:"POST",
	                    url:getUrl()+'/Api/OrderStyle/delOrderStyle',
	                    dataType:'json',
	                    data:{
	                    	Id:styleVue.myChecked_big[0].id
	                    },
	                    success:function(data){
	                        
	                        if(data.success){	
	                        	this.choice_numbers=0;
	                        	Notify('删除成功！', 'top-right', '5000', 'primary', 'fa-circle-o', true);                  	
								styleVue.init();
								styleVue.smallClasses.splice(0,styleVue.smallClasses.length);
								styleVue.refreshSmallClass(1);
								
	                   		}else{
	                   			Notify('无法删除！', 'top-right', '5000', 'danger', 'fa-circle-o', true);                  	
								
	                   		}
	               
	                    },
	                    error:function(msg){
	
	                    	}
                		});
					
									
				
			},
			deleteBClass_Failed:function(str){
				Notify("类名为"+str+"的大类无法删除！删除失败！", 'top-right', '5000', 'danger', 'fa-circle-o', true);                  	
								
			},
			updateClass:function(){
				if(styleVue.type==1){
					$("#updatebigClassmodal").modal('show');
					$("#dl_error2").text('原名：'+styleVue.myChecked_big[0].name);
					$("#dl_error2").show();
				}else if(styleVue.type==2){
					$("#updatesmallClassmodal").modal('show');
					$("#error2").text('原名：'+styleVue.myChecked_small2[0]);
					$("#error2").show();
				}
			},
			updateClassname:function(){
				if(this.type==1){
					var stylename=$("#bigclassname2").val();
					if(stylename==""){
						$("#dl_error2").text('名字不能为空！');
						$("#dl_error2").show();
					}else{
					
						$.ajax({
	                    type:"POST",
	                    url:getUrl()+'/Api/OrderStyle/updateOrderStyle',
	                    dataType:'json',
	                    data:{
	                    	id:styleVue.myChecked_big[0].id,
	                    	stylename:stylename
	                    },
	                    success:function(data){
	                      
	                        if(data.success){	
	                        	this.choice_numbers=0;
	                        	$("#dl_error2").hide();
	                        	Notify('修改成功！', 'top-right', '5000', 'primary', 'fa-circle-o', true);                  	
								styleVue.init();
								styleVue.smallClasses.splice(0,styleVue.smallClasses.length);
								$("#updatebigClassmodal").modal('hide');								
								styleVue.refreshSmallClass(1);
								
								
	                   		}else{
	                   			Notify('修改失败！', 'top-right', '5000', 'danger', 'fa-circle-o', true);                  	
								
	                   		}
	               
	                    },
	                    error:function(msg){
	
	                    	}
                		});
					}
					
					
				}else if(this.type==2){
					var typename=$("#smallclassname2").val();
					if(typename==""){
						$("#error2").text('名字不能为空！');
						$("#error2").show();
					}else{
						$.ajax({
	                    type:"POST",
	                    url:getUrl()+'/Api/OrderStyle/updateOrderType',
	                    dataType:'json',
	                    data:{
	                    	pk_only:styleVue.myChecked_small[0],
	                    	typename:typename
	                    },
	                    success:function(data){
	                        
	                        if(data.success){	
	                        	this.choice_numbers=0;
	                        	$("#error2").hide();
	                        	Notify('修改成功！', 'top-right', '5000', 'primary', 'fa-circle-o', true);                  	
								styleVue.init();
								styleVue.smallClasses.splice(0,styleVue.smallClasses.length);								
								$("#updatesmallClassmodal").modal('hide');
								
								styleVue.refreshSmallClass(2);
	                   		}else{
	                   			Notify('修改失败！', 'top-right', '5000', 'danger', 'fa-circle-o', true);                  	
								
	                   		}
	               
	                    },
	                    error:function(msg){
	
	                    	}
                		});
					}
				}
			},
			addClassName:function(id){
				if(id==1){
					var stylename=$("#bigclassname").val();
					if(stylename==""){						
						$("#dl_error").text('名字不能为空！');
						$("#dl_error").show();
					}else{
						$.ajax({
							type:"POST",
							url:getUrl()+'/Api/OrderStyle/addOrderStyle',
							dataType:'json',
							data:{
								stylename:stylename,
							},
							success:function(data){
								
								if(data.success){	
									$("#dl_error").hide();
									Notify('添加成功！', 'top-right', '5000', 'primary', 'fa-circle-o', true);                  	
									$("#addbigClassmodal").modal('hide');
									styleVue.init();
									styleVue.smallClasses.splice(0,styleVue.smallClasses.length);
									styleVue.refreshSmallClass(1);
								}else{									
									$("#dl_error").text(data.message);
									$("#dl_error").show();
									}
					   
							},
							error:function(msg){
		
							 }
							});
					}
					
				}else if(id==2){
					var typename=$("#smallclassname").val();
					if(typename==""){						
						$("#error").text('名字不能为空！');
						$("#error").show();
					}else{
						$.ajax({
							type:"POST",
							url:getUrl()+'/Api/OrderStyle/addOrderType',
							dataType:'json',
							data:{
								typename:typename,
								pk_parentid:this.pk_parentid
							},
							success:function(data){
								
								if(data.success){	
									$("#error").hide();	
									Notify('添加成功！', 'top-right', '5000', 'primary', 'fa-circle-o', true);                  	
									$("#addsmallClassmodal").modal('hide');
									styleVue.refreshSmallClass(2);
								}else{
								$("#error").text(data.message);
								$("#error").show();	
								}
					   
							},
							error:function(msg){
		
							 }
							});
					
					}
					
				}
		},
			refreshSmallClass:function(id){				
				if(id==1){
					$.ajax({
                    type:"POST",
                    url:getUrl()+"/Api/OrderStyle/findStyle",
                    dataType:"JSON",
                    success:function(data){
                    	styleVue.smallClasses.splice(0,styleVue.smallClasses.length);
                        for(var i=0;i<data.data.length;i++){
                            styleVue.smallClasses.push({
                            	pk_only:data.data[i].id,
                            	typename:data.data[i].stylename
                            	
                            });
                            
                        }
                        
                    },
                    error:function(msg){
                        console.log(msg);
                    }
                });
				}else if(id==2){
					$.ajax({
	                    type:"POST",
	                    url:getUrl()+'/Api/OrderStyle/findtype',
	                    dataType:'json',
	                    data:{
	                    	pk_parentid:styleVue.pk_parentid
	                    },
	                    success:function(data){
	                        if(data.success){
								styleVue.smallClasses.splice(0,styleVue.smallClasses.length);
	                        	this.nodata=false;
		                    	for(x=0;x<data.data.length;x++){                    	
		                    		styleVue.smallClasses.push(data.data[x]);
		                   		}	
	                   		}else{
	                   			this.nodata=true;
	                   		}
	               
	                    },
	                    error:function(msg){
	
	                    }
                	});
				}
				
			},
		}
	});
	styleVue.init();
	
/*	$("#allGoods_a").click(function(){
		$("#allGoods").toggleClass('active1');
	});*/
	 $("#checkAll").click(function(){  
           if($(this).prop("checked")){//全选  
               $("input[name='checked']").attr("checked","true");  
               if(styleVue.type==1){
               	$("#delete").removeAttr('disabled');
               	$("#update").attr('disabled',true);
               $("#table").find('tr').each(function(){
               	             	
               		styleVue.myChecked_big.push({
               			id:$(this).children('.pk_only').text(),
               			name:$(this).children('.typename').text()
               		})
               		
               
               
               });
               for(var j=0;j<styleVue.myChecked_big.length;j++){
               		if(styleVue.myChecked_big[j].id==""){
               			styleVue.myChecked_big.splice(j,1);
               			j=j-1;
               		}
               }
              
               	}else if(styleVue.type==2){
               		$("#delete").removeAttr('disabled');
               		$("#update").attr('disabled',true);
               		$("#table").find('tr').each(function(){
               			styleVue.myChecked_small.push($(this).children('.pk_only').text());
               		
               		});
               		for(var j=0;j<styleVue.myChecked_small.length;j++){
               		if(styleVue.myChecked_small[j]==""){
               			styleVue.myChecked_small.splice(j,1);
               			j=j-1;
               		}
               }
               		
               		
               	}
           }else{//全不选         
           		$("#delete").attr('disabled',true);
           		$("#update").attr('disabled',true);
               $("input[name='checked']").prop('checked',false);  
               	if(styleVue.type==1){               		
               		styleVue.myChecked_big.splice(0,styleVue.myChecked_big.length);
               		
               	}else if(styleVue.type==2){
               		styleVue.myChecked_small.splice(0,styleVue.myChecked_small.length);
               		
               	}
           }  
       }); 
     function trimStr(str){
     	return str.replace(/(^\s*)|(\s*$)/g,"");
     }
})
