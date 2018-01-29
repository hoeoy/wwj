var img_url='/upload/1495694599854.jpg';
$(function(){
	var goodsVue=new Vue({
		el:"#all_content_style",
		data:{
			conurl:getUrl(),
			outClasses:[],//所有大类
			smallClasses:[],//小类
			allGoods:[], //商品集合
			parentClass:'',
			nodata:false,
			choiceNumbers:0,
			type:0,//类别：0为未选择  1为大类  2为小类
			myChecked_small:[], //我的小类选中
			open:false,
			myChecked_big:[], //我的大类选中
			pk_parentid:'',
			choice_numbers:0,
			currentPage:1,
			my_checked_goods:[],
			currentId:'',
			currentName:'',
			id:'',
			name:'',
			unit:'',
			norm:'',
			goodstype:'',
			image:'/upload/1495694599854.jpg', //默认图片地址
			price:'',
			barcode:'',
			state:'',
			totalpages:1,
			pagesArrys:[],
			searchType:1,
			
			
		},
		methods:{
			init:function(){
				goodsVue.outClasses.splice(0,goodsVue.outClasses.length);
				 $.ajax({
                    type:"POST",
                    url:getUrl()+"/Api/OrderStyle/findStyle",
                    dataType:"JSON",
                    success:function(data){
                        for(var i=0;i<data.data.length;i++){
                            goodsVue.outClasses.push(data.data[i]);    
                        }
                        
                    },
                    error:function(msg){
                        console.log(msg);
                    }
                });
			},
			choiceBClass:function(e){
				this.type=0;
				goodsVue.smallClasses.splice(0,goodsVue.smallClasses.length);
				var parentid=e.currentTarget.parentNode.querySelector('.my_bigid').outerText;
				$("#allGoods").children('li').removeClass('active1');
				e.currentTarget.parentNode.className='active1';
				if(this.open==false){
					e.currentTarget.querySelector('.glyphicon').className='glyphicon glyphicon-minus menu_title2';
					this.open=true;
				}else{
						e.currentTarget.querySelector('.glyphicon').className='glyphicon glyphicon-plus menu_title2';
					this.open=false;
				
				}
				
				$.ajax({
	                    type:"POST",
	                    url:getUrl()+'/Api/OrderStyle/findtype',
	                    dataType:'json',
	                    data:{
	                    	pk_parentid:parentid
	                    },
	                    success:function(data){
	                      
	                        if(data.success){	
	                        	for(var i=0;i<data.data.length;i++){
	                        		goodsVue.smallClasses.push(data.data[i]);
	                        	}
	                        		
	                   		}else{
	                   			
	                   		}
	               
	                    },
	                    error:function(msg){
	
	                    	}
                		});
			},
			choiceSClass:function(e){			
				this.type=2;
				this.choice_numbers=0;
				goodsVue.searchType=1;
				goodsVue.currentPage=1;
				goodsVue.allGoods.splice(0,goodsVue.allGoods.length);
				this.currentId=e.currentTarget.parentNode.querySelector('.my_smallid').outerText;
				this.currentName=e.currentTarget.parentNode.querySelector('.menu_title2').outerText;
				if(e.currentTarget.parentNode.parentNode.querySelector('.active2')==null){
					
				}else{
					e.currentTarget.parentNode.parentNode.querySelector('.active2').className='';
				}
				//
				e.currentTarget.parentNode.className='active2';
				$.ajax({
	                    type:"POST",
	                    url:getUrl()+'/Api/Order/Findtypegoods',
	                    dataType:'json',
	                    data:{
	                    	goodstype:this.currentId
	                    },
	                    success:function(data){

	                        if(data.success){
	                        	goodsVue.totalpages=data.data.pages

	                        	for(var i=0;i<data.data.list.length;i++){
	                        		goodsVue.allGoods.push(data.data.list[i]);
	                        	}
	                        	if(goodsVue.totalpages>1){	                        		
	                        		goodsVue.pagesArrys.splice(0,goodsVue.pagesArrys.length);
	                        		goodsVue.pagesArrys.push('首页','上一页','下一页','尾页');
		                    		var myindex=1;
			                       	for(var i=0;i<goodsVue.totalpages;i++){
			                       		myindex=myindex+1;
			                       		goodsVue.pagesArrys.splice(myindex,0,i+1);
			                       	} 
			                       	$("#fydh").show();
	                        	}else{
	                        		$("#fydh").hide();
	                        	}
	                   		}else{
	                   			$("#fydh").hide();
	                   		}
	               
	                    },
	                    error:function(msg){
	
	                    	}
                		});
			},
			addGoodshow:function(){
				if(this.type==2){
					$("#warn-tips").text('');
					$("#good_name").attr("value","");
					$("#good_spec").attr("value","");
					$("#good_unit").attr("value","");
					$("#good_price").attr("value","");
					$("#good_unicode").attr("value","");
					$("#addGoodsModal").modal('show');
				}else{
					Notify('请选择一个小类','top-right','5000', 'danger', 'fa-times-circle', true);
				}
			},
			addGoods:function(){
				var goodsname=$("#good_name").val();
				var norm=$("#good_spec").val();
				var unit=$("#good_unit").val();
				var price=$("#good_price").val();
				var state;
				if($("#isUp").prop('checked')){
					state=1;
				}else{
					state=0;
				}
				var barcode=$("#good_unicode").val();
				var img=img_url;

				$.ajax({
	                    type:"POST",
	                    url:getUrl()+'/Api/Goods/insertGoods',
	                    dataType:'json',
	                    data:{
	                    	goodsname:goodsname,
	                    	styleid:this.currentId,
	                    	norm:norm,
	                    	unit:unit,
	                    	price:price,
	                    	state:state,
	                    	image:img,
	                    	barcode:barcode
	                    },
	                    success:function(data){

	                        if(data.success){	
	                        	Notify('添加成功','top-right','5000', 'primary', 'fa-times-circle', true);
								goodsVue.refreshGoods();
	                       		$("#addGoodsModal").modal('hide');
	                       		$("#good_name").attr('value','');
								$("#good_spec").attr('value','');
								$("#good_unit").attr('value','');
								$("#good_price").attr('value','');
								$("#warn-tips").text('');
	                   		}else{
	                   			Notify('添加失败','top-right','5000', 'danger', 'fa-times-circle', true);
								
	                   		}
	               
	                    },
	                    error:function(msg){
	
	                    	}
                		});
			},
			checked:function(e){
				this.id=e.currentTarget.parentNode.parentNode.querySelector('.id').outerText;
				this.name=e.currentTarget.parentNode.parentNode.querySelector('.goodsname').outerText;				
				this.norm=e.currentTarget.parentNode.parentNode.querySelector('.norm').outerText;
				this.unit=e.currentTarget.parentNode.parentNode.querySelector('.unit').outerText;				
				this.price=e.currentTarget.parentNode.parentNode.querySelector('.price').outerText;
				this.goodstype=e.currentTarget.parentNode.parentNode.querySelector('.goodstype').outerText;				
				this.barcode=e.currentTarget.parentNode.parentNode.querySelector('.barcode').outerText;
				this.state=e.currentTarget.parentNode.parentNode.querySelector('.state').outerText;				
				this.image=e.currentTarget.parentNode.parentNode.querySelector('.image').outerText;								
				img_url=this.image;
				if(e.currentTarget.checked){
					this.choice_numbers+=1;

					this.my_checked_goods.push({
						id:this.id,
						name:this.name,
						norm:this.norm,
						unit:this.unit,
						price:this.price,
						state:this.state,
						barcode:this.barcode,
						image:this.image,
						styleid:this.currentId,
						goodstype:this.goodstype
					});
				}else{
					this.choice_numbers-=1;
					for(var i=0;i<this.my_checked_goods.length;i++){
						if(this.my_checked_goods[i].id==this.id){
							this.my_checked_goods.splice(i,1);
							break;
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
				}else{
					$("#delete").attr('disabled',true);
					$("#update").attr('disabled',true);
				}
				if(this.choice_numbers==this.allGoods.length){
					$("#checkAll").prop('checked',true);
				}else{
					$("#checkAll").prop('checked',false);
				}

			},
			deleteGoods:function(){
				for(var i=0;i<goodsVue.my_checked_goods.length;i++){
					$.ajax({
	                    type:"POST",
	                    url:getUrl()+'/Api/Order/delgood',
	                    dataType:'json',
	                    data:{
	                    	id:goodsVue.my_checked_goods[i].id
	                    },
	                    success:function(data){

	                        if(data.success){	
	                        	Notify('删除成功，请刷新页面','top-right','5000', 'primary', 'fa-times-circle', true);								
								goodsVue.choice_numbers=goodsVue.choice_numbers-1;

								$("#update").attr('disabled',true);
								$("#delete").attr('disabled',true);
		
	                   		}else{
	                   			Notify('删除失败','top-right','5000', 'danger', 'fa-times-circle', true);
					
	                   		}
	               		
	                    },
	                    error:function(msg){
	
	                    	}
                		});
				}
				
				goodsVue.refreshGoods();
			},
			refreshGoods:function(){
				goodsVue.my_checked_goods.splice(0,goodsVue.my_checked_goods.length);	
				goodsVue.allGoods.splice(0,goodsVue.allGoods.length);
				$.ajax({
	                    type:"POST",
	                    url:getUrl()+'/Api/Order/Findtypegoods',
	                    dataType:'json',
	                    data:{
	                    	goodstype:goodsVue.currentId,
	                    	pageNo:goodsVue.currentPage
	                    },
	                    success:function(data){

	                        if(data.success){
	                        	for(var i=0;i<data.data.list.length;i++){
	                        		goodsVue.allGoods.push(data.data.list[i]);
	                        	}
	                        	if(data.data.pages<2){
	                        		$("#fydh").hide();
	                        		goodsVue.totalpages=data.data.pages;
	                        	}else{
	                        		goodsVue.totalpages=data.data.pages;
	                        		goodsVue.pagesArrys.splice(0,goodsVue.pagesArrys.length);
	                        		goodsVue.pagesArrys.push('首页','上一页','下一页','尾页');
		                    		var myindex=1;
			                       	for(var i=0;i<goodsVue.totalpages;i++){
			                       		myindex=myindex+1;
			                       		goodsVue.pagesArrys.splice(myindex,0,i+1);
			                       	} 
	                        		$("#fydh").show();
	                        		
	                        	}
	                        	
	                        		
	                   		}else{
	                   			
	                   		}
	               
	                    },
	                    error:function(msg){
	
	                    	}
                		});
			},
			updateGoodshow:function(){
				$("#updateGoodsModal").modal('show');
			},
			updateGoods:function(){
				var state;var _this = this;
				if($("#state").prop('checked')){
					state=1;
				}else{
					state=0;
				}
				$.ajax({
	                    type:"POST",
	                    url:getUrl()+'/Api/Order/updategood',
	                    dataType:'json',
	                    data:{
	                    	id:this.id,
	                    	goodsname:this.name,
	                    	norm:this.norm,
	                    	unit:this.unit,
	                    	price:this.price,
	                    	barcode:this.barcode,
	                    	image:img_url,
	                    	styleid:this.currentId,
	                    	state:state
	                    },
	                    success:function(data){

	                        if(data.success){	
	                        	Notify('修改成功','top-right','5000', 'primary', 'fa-times-circle', true);
                                _this.choice_numbers=0;
								$("#update").attr('disabled',true);
								$("#delete").attr('disabled',true);
								goodsVue.refreshGoods();
	                        	$("#updateGoodsModal").modal('hide');
	                        	$("#warn-tips2").text('');
	                        		
	                   		}else{
	                   				Notify('修改失败','top-right','5000', 'danger', 'fa-times-circle', true);
							
	                   		}
	               
	                    },
	                    error:function(msg){
	
	                    	}
                		});
			},
			toPage:function(e){

					var str=e.currentTarget.querySelector('a').outerText;	
            		if(str=="首页" || str=="上一页" || str=="下一页" || str=="尾页"){
            		if(str=="首页"){
            			$("#fydh").children('li').removeClass('active_');
	            		goodsVue.currentPage=1;
	            		var data_id=e.currentTarget.parentNode.children[2];          		
            			data_id.className='active_';
            		}else if(str=="尾页"){
            			$("#fydh").children('li').removeClass('active_');
            			goodsVue.currentPage=goodsVue.totalpages;

            			e.currentTarget.parentNode.children[goodsVue.totalpages+1].className='active_';
            		}else if(str=="下一页"){                 			
            			if(goodsVue.currentPage<goodsVue.totalpages){
            				$("#fydh").children('li').removeClass('active_');
            				goodsVue.currentPage=goodsVue.currentPage+1;
            				e.currentTarget.parentNode.children[goodsVue.currentPage+1].className='active_';
            			}else if(goodsVue.currentPage==goodsVue.pages){
            				
            			}
            		}else if(str=="上一页"){
            			
            			if(goodsVue.currentPage>1){
            				$("#fydh").children('li').removeClass('active_');
            				goodsVue.currentPage=goodsVue.currentPage-1;
            				e.currentTarget.parentNode.children[goodsVue.currentPage+1].className='active_';
            			
            			}else if(goodsVue.currentPage==1){
            				
            			}
            		}
            	}else{
            		$("#fydh").children('li').removeClass('active_');
            		e.currentTarget.className='active_';
            		goodsVue.currentPage=e.currentTarget.querySelector('a').outerText;
            	}
            	
            if(this.searchType==1){
            	$.ajax({
                    type:"POST",
                    url:getUrl()+'/Api/Order/Findtypegoods',
                    dataType:'json',
                    data:{
                    	goodstype:this.currentId,
                    	pageNo:goodsVue.currentPage
                    },
                    success:function(data){

                        if(data.success){
                        	goodsVue.allGoods.splice(0,goodsVue.allGoods.length);
                        	for(var j=0;j<data.data.list.length;j++){
                            goodsVue.allGoods.push(data.data.list[j]);                           
                       	 }
                        }
    
                    },
                    error:function(msg){
						console.log(msg);
                    }
                });
				}else if(this.searchType==2){
					var goodname=$("#goods_name").val();
				var code=$("#goods_code").val();
				$.ajax({
                    type:"POST",
                    url:getUrl()+'/Api/Order/Findgood',
                    dataType:'json',
                    data:{
                    	goodsname:goodname,
                    	barcode:code,
                    	pageNo:goodsVue.currentPage
                    },
                    success:function(data){

                        if(data.success){
                        	goodsVue.allGoods.splice(0,goodsVue.allGoods.length);
                        	for(var j=0;j<data.data.list.length;j++){
                            goodsVue.allGoods.push(data.data.list[j]);                           
                       	 }
                     
                        }
    
                    },
                    error:function(msg){
						console.log(msg);
                    }
                });
				}
			},
			toSearch:function(){
				goodsVue.searchType=2;
				goodsVue.currentPage=1;
				var goodname=$("#goods_name").val();
				var code=$("#goods_code").val();
				$.ajax({
                    type:"POST",
                    url:getUrl()+'/Api/Order/Findgood',
                    dataType:'json',
                    data:{
                    	goodsname:goodname,
                    	barcode:code
                    },
                    success:function(data){

                        if(data.success){
                        	goodsVue.allGoods.splice(0,goodsVue.allGoods.length);
                        	for(var j=0;j<data.data.list.length;j++){
                            goodsVue.allGoods.push(data.data.list[j]);                           
                       	 }
                        	goodsVue.totalpages=data.data.pages;
                        	if(goodsVue.totalpages>1){
                        			goodsVue.pagesArrys.splice(0,goodsVue.pagesArrys.length);
	                        		goodsVue.pagesArrys.push('首页','上一页','下一页','尾页');
		                    		var myindex=1;
			                       	for(var i=0;i<goodsVue.totalpages;i++){
			                       		myindex=myindex+1;
			                       		goodsVue.pagesArrys.splice(myindex,0,i+1);
			                       	} 
	                        		$("#fydh").show();
                        	}
                        }else{
                        	goodsVue.allGoods.splice(0,goodsVue.allGoods.length);
                        	$("#fydh").hide();
                        }
    
                    },
                    error:function(msg){
						console.log(msg);
                    }
                });
			},
			changefile:function(){
				$("#uploda_img").removeAttr('disabled');
		 		$("#small_sure").attr('disabled',true);
			},
			changefile2:function(){
				$("#uploda_img2").removeAttr('disabled');
		 		$("#update_sure").attr('disabled',true);
			},

		},
			
	});
	goodsVue.init();
	
/*	$("#allGoods_a").click(function(){
		$("#allGoods").toggleClass('active1');
	});*/
	 $("#checkAll").click(function(){  
           if($(this).prop("checked")){//全选  
               $("input[name='checked']").attr("checked","true");  
  				$("#delete").removeAttr('disabled');
  				 $("#table").find('tr').each(function(){               	             	
               		goodsVue.my_checked_goods.push({
               			id:$(this).children('.id').text(),
               			name:$(this).children('.goodsname').text()
               		});
               });
               for(var j=0;j<goodsVue.my_checked_goods.length;j++){
               		if(goodsVue.my_checked_goods[j].id==""){
               			goodsVue.my_checked_goods.splice(j,1);
               			j=j-1;
               		}               		
               		this.choice_numbers=goodsVue.my_checked_goods.length;
               }
           }else{//全不选         
           	   $("#delete").attr('disabled',true);
               $("input[name='checked']").prop('checked',false);     
               goodsVue.my_checked_goods.splice(0,goodsVue.my_checked_goods.length);
                this.choice_numbers=0;
           }  
       }); 

     
	   function trimStr(str){
     	return str.replace(/(^\s*)|(\s*$)/g,"");
     }
})
$(document).ready(function() { 
	 $("#uploadform").submit(function(){
	 		$("#uploadform").ajaxSubmit({  
                  success: function(data) { // data 保存提交后返回的数据，一般为 json 数据  
                    if(data.success){

                    	$("#warn-tips").text(data.errCode+"!");
        				img_url=data.operateCode;

                    	$("#small_sure").removeAttr('disabled');
                    }else{
                    	$("#warn-tips").text(data.errCode+"!");
                    }    
                  }  
                  
              }); 
              return false;
	 });
	 $("#updateG").submit(function(){
	 		$("#updateG").ajaxSubmit({  
                  success: function(data) { // data 保存提交后返回的数据，一般为 json 数据  
                    if(data.success){
                    	$("#warn-tips2").text(data.errCode+"!");
        				img_url=data.operateCode;

                    	$("#update_sure").removeAttr('disabled');
                    }else{
                    	$("#warn-tips2").text(data.errCode+"!");
                    }    
                  }  
                  
              }); 
              return false;
	 });
})