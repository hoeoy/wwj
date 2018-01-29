var mealVue=new Vue({
    el:"#allcontent",
    data:{
        result_txt:'等待结算....'
    },
    methods:{
        sure:function(){
            var start=$("#start_ts").val();
            var end=$("#end_ts").val();
            this.result_txt='正在结算，请稍候...';
            console.log(mealVue.compare(start));
            console.log(mealVue.compare(end));
            if(mealVue.compare(start) ||mealVue.compare(end)){
                mealVue.result_txt='日期错误，不能大于当前日期';
            }else{
                $.ajax({
                    type:'post',
                    url:'/Api/chargeSum/updrecordsum',
                    dataType:'JSON',
                    data:{
                        start_ts:start,
                        end_ts:end
                    },
                    success:function(data){
                        console.log(data);
                        if(data.success){
                            mealVue.result_txt='结算完毕';
                            $(".right").show();
                        }else{
                            mealVue.result_txt=data.errCode;
                        }

                    },
                    error:function(msg){
                        mealVue.result_txt='数据库连接失败';
                        console.log(msg);
                    }
                })
            }




        },
        hideright:function(){
            $(".right").hide();
        },
        compare:function(time){
            var d=new Date(Date.parse(time .replace(/-/g,"/")));
            var curDate=new Date();
            if(d>curDate){
               return true;
            }
        }
    }
});
