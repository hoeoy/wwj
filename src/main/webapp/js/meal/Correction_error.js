$(function(){
	var CorrVue=new Vue({
		el:"#all_content",
		data:{
			contents:[],
			choice:0,
			list:[],
			codeId:'',//卡号
			lists:{
				pk_meal_record:'',
				pk_device:'',
				pk_card:'',
				staff_code:'',
				device_code:'',
				meal_type:'',
				device_meal_type:'',
				dining_name:'',
				dining_code:'',
				meal_way:'',
				pk_staff:'',
				card_code:'',
				staff_name:'',
				meal_ts:'',
				meal_money:'',
				meal_allowance:'',
				allowance_retain:'',
				meal_cash_money:'',
				cash_retain:'',
				money_retain:'',
				money:''
			}
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

			search:function(){
				CorrVue.contents.splice(0,CorrVue.contents.length);
				this.choice=0;
				$("#btn_query").attr('disabled',true);
				$("#btn_query").text('查询中...');
				var cardId=$("#codeId").val();
				var startTime=$("#start_time").val();
				var endTime=$("#end_time").val()+"&nbsp;23:59:59";

				$.ajax({
					type: 'post',
					data:{
						card_code:cardId,
						start_ts:startTime,
						end_ts:endTime
					},
					dataType:"json",
					url: getUrl() + '/Api/MealrecordUp/queryorder',
					success: function (data) {
						$("#btn_query").removeAttr('disabled');
						$("#btn_query").text('查询');
						console.log(data);
						for(var i=0;i< data.data.length;i++){
							CorrVue.contents.push(data.data[i]);
						}
					},
					error: function (data) {
						$("#btn_query").removeAttr('disabled');
						$("#btn_query").text('查询');
						Notify('查询失败，请检查卡号与日期', 'top-right', '5000', 'danger', 'fa-times-circle', true);
					}
				});

			},
			checked:function(e){
				if(e.currentTarget.checked){
					this.choice+=1;
					var pk_meal_record= e.currentTarget.parentNode.parentNode.querySelector('.pk_meal_record').outerText;
					var pk_device= e.currentTarget.parentNode.parentNode.querySelector('.pk_device').outerText;
					var pk_card= e.currentTarget.parentNode.parentNode.querySelector('.pk_card').outerText;
					var staff_code= e.currentTarget.parentNode.parentNode.querySelector('.staff_code').outerText;
					var device_code= e.currentTarget.parentNode.parentNode.querySelector('.device_code').outerText;
					var meal_type= e.currentTarget.parentNode.parentNode.querySelector('.meal_type').outerText;
					var device_meal_type= e.currentTarget.parentNode.parentNode.querySelector('.device_meal_type').outerText;
					var dining_name= e.currentTarget.parentNode.parentNode.querySelector('.dining_name').outerText;
					var dining_code= e.currentTarget.parentNode.parentNode.querySelector('.dining_code').outerText;
					var meal_way= e.currentTarget.parentNode.parentNode.querySelector('.meal_way').outerText;
					var staff_name= e.currentTarget.parentNode.parentNode.querySelector('.staff_name').outerText;
					var card_code= e.currentTarget.parentNode.parentNode.querySelector('.card_code').outerText;
					var meal_ts= e.currentTarget.parentNode.parentNode.querySelector('.meal_ts').outerText;
					var device_name= e.currentTarget.parentNode.parentNode.querySelector('.device_name').outerText;
					var meal_money= e.currentTarget.parentNode.parentNode.querySelector('.meal_money').outerText;
					var meal_allowance= e.currentTarget.parentNode.parentNode.querySelector('.meal_allowance').outerText;
					var allowance_retain= e.currentTarget.parentNode.parentNode.querySelector('.allowance_retain').outerText;
					var meal_cash_money= e.currentTarget.parentNode.parentNode.querySelector('.meal_cash_money').outerText;
					var cash_retain= e.currentTarget.parentNode.parentNode.querySelector('.cash_retain').outerText;
					var money_retain= e.currentTarget.parentNode.parentNode.querySelector('.money_retain').outerText;
					var pk_staff= e.currentTarget.parentNode.parentNode.querySelector('.pk_staff').outerText;
					CorrVue.list.push({
						pk_meal_record:pk_meal_record,
						pk_device:pk_device,
						pk_card:pk_card,
						staff_code:staff_code,
						device_code:device_code,
						meal_type:meal_type,
						device_meal_type:device_meal_type,
						dining_name:dining_name,
						dining_code:dining_code,
						meal_way:meal_way,
						pk_staff:pk_staff,
						card_code:card_code,
						staff_name:staff_name,
						meal_ts:meal_ts,
						meal_money:meal_money,
						meal_allowance:meal_allowance,
						allowance_retain:allowance_retain,
						meal_cash_money:meal_cash_money,
						cash_retain:cash_retain,
						money_retain:money_retain
					})
				}else{
					this.choice-=1;
					var pk_staff1= e.currentTarget.parentNode.querySelector('.pk_staff').outerText;
					var i;
					for(i=0;i<this.list.length;i++){
						if(this.list[i].pk_staff==pk_staff1){
							this.list.splice(i,1);
						}
					}
				}
				if(this.choice==1){
					$("#btn_gz_cor").removeAttr('disabled');
				}else{
					$("#btn_gz_cor").attr('disabled',true);
				}
				console.log(this.list);
			},
			changeMoney:function(){
				$(".corr_tips").text('');
				var money=parseFloat($("#money").val());
				console.log(money<this.list[0].meal_money,this.list[0].meal_money);
				if(money==""){
					$(".corr_tips").text('金额不能为空');
				}else if(money>=0 && money<this.list[0].meal_money){
					console.log(this.list[0]);
					$("#gengzheng").attr('disabled',true);
					$("#gengzheng").text('更正中...');
					$.ajax({
						type: 'post',
						data:{
							pk_meal_record:CorrVue.list[0].pk_meal_record,
							pk_device:CorrVue.list[0].pk_device,
							pk_card:CorrVue.list[0].pk_card,
							staff_code:CorrVue.list[0].staff_code,
							device_code:CorrVue.list[0].device_code,
							meal_type:CorrVue.list[0].meal_type,
							device_meal_type:CorrVue.list[0].device_meal_type,
							dining_name:CorrVue.list[0].dining_name,
							dining_code:CorrVue.list[0].dining_code,
							meal_way:CorrVue.list[0].meal_way,
							pk_staff:CorrVue.list[0].pk_staff,
							card_code:CorrVue.list[0].card_code,
							staff_name:CorrVue.list[0].staff_name,
							meal_ts:CorrVue.list[0].meal_ts,
							meal_money:CorrVue.list[0].meal_money,
							meal_allowance:CorrVue.list[0].meal_allowance,
							allowance_retain:CorrVue.list[0].allowance_retain,
							meal_cash_money:CorrVue.list[0].meal_cash_money,
							cash_retain:CorrVue.list[0].cash_retain,
							money_retain:CorrVue.list[0].money_retain,
							money:money
						},
						dataType:"json",
						url: getUrl() + '/Api/MealrecordUp/updateproceeds',
						success: function (data) {
							console.log(data);
							$("#gengzheng").removeAttr('disabled');
							$("#gengzheng").text('更正');
							if(data.success){
								Notify("更正成功", 'top-right', '5000', 'primary', 'fa-circle-o', true);
								$("#changeModal").modal('hide');
								this.search();
							}else{
								Notify("更正失败", 'top-right', '5000', 'danger', 'fa-circle-o', true);
							}
						},
						error: function (msg) {
							console.log(msg);
							$("#gengzheng").removeAttr('disabled');
							$("#gengzheng").text('更正');
						}
					});

				}else{
					$(".corr_tips").text('错误的金额');
				}
			},
			readCard:function(){
				console.log('读卡');
				var v = AxRead.RequestPhyNo( ) ;// 调取硬件读取卡号
				console.log(AxRead)
				if( v == 0 )  Notify('没有检测到卡或者发卡器', 'top-right', '5000', 'danger', 'fa-times-circle', true);
				else this.codeId = v;
			},
		}
	});
	CorrVue.init();
})
