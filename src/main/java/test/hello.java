package test;

import com.iandtop.model.form.MerchantPos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seven on 2017/8/18.
 */
public class hello {
    public static void main(String[] args){
        String str = "waiwenjun/afefefef";
        int i  =  str.indexOf("/");

        str = str.substring(i +1);
        System.out.println(i +"helo=" +str);

        sumMerchantPosCont();
    }

    private static void sumMerchantPosCont() {
        List<MerchantPos> ret = new ArrayList<MerchantPos>();

        List<MerchantPos> mercharCurrentList = new ArrayList<MerchantPos>();
        List<MerchantPos> merchantNextList = new ArrayList<MerchantPos>();

        for(int n = 0; n < mercharCurrentList.size(); n++){
            MerchantPos merchantCurrent =  mercharCurrentList.get(n);
            Long pkMerchantCurrent = merchantCurrent.getPkMerchant();
            for(int h = 0; h < merchantNextList.size(); h++){
                MerchantPos merchantNext =  merchantNextList.get(n);
                Long pkMerchantNext = merchantNext.getPkMerchant();

                if (pkMerchantCurrent == pkMerchantNext){
                    // 咖啡
                    Long cofferAmount = merchantCurrent.getCofferAmount() + merchantNext.getCofferAmount();
                    double cofferPrice = merchantCurrent.getCofferPrice() + merchantNext.getCofferPrice();
                    // 超市
                    Long marketAmount = merchantCurrent.getMarketAmout() + merchantNext.getMarketAmout();
                    double marketPrice = merchantCurrent.getMarketPrice() + merchantNext.getMarketPrice();
                    // 早餐
                    Long brkAmount = merchantCurrent.getBreakfastAmount() + merchantNext.getBreakfastAmount();
                    double brkPrice = merchantCurrent.getBreakfastPrice() + merchantNext.getBreakfastPrice();
                    // 中餐
                    Long lunchAmount = merchantCurrent.getLunchAmount() + merchantNext.getLunchAmount();
                    double lunchPrice = merchantCurrent.getLunchPrice() + merchantNext.getLunchPrice();
                    // 外卖
                    Long orderAmount = merchantCurrent.getOderAmount() + merchantNext.getOderAmount();
                    double orderPrice = merchantCurrent.getOderPrice() + merchantNext.getOderPrice();
                    // 晚餐
                    Long dinnerAmount = merchantCurrent.getDinnerAmount() + merchantNext.getDinnerAmount();
                    double dinnerPrice = merchantCurrent.getDinnerPrice() + merchantNext.getDinnerPrice();
                    // 总消费
                    Long totalAmount = merchantCurrent.getTotalAmount() + merchantNext.getTotalAmount();
                    double totalPrice = merchantCurrent.getTotalPrice() + merchantNext.getOderPrice();

                    /* 构造输出 */
                    MerchantPos merchantRet = new  MerchantPos();
                    // 主键
                    merchantRet.setPkMerchant(merchantCurrent.getPkMerchant());
                    // 商户编码
                    merchantRet.setMerchantCode(merchantCurrent.getMerchantCode());
                    // 商户名称
                    merchantRet.setMerchantName(merchantCurrent.getMerchantName());
                    // 咖啡
                    merchantRet.setCofferAmount(cofferAmount);
                    merchantRet.setCofferPrice(cofferPrice);
                    // 超市
                    merchantRet.setMarketAmout(marketAmount);
                    merchantRet.setMarketPrice(marketPrice);
                    // 早餐
                    merchantRet.setBreakfastAmount(brkAmount);
                    merchantRet.setBreakfastPrice(brkPrice);
                    // 午餐
                    merchantRet.setLunchAmount(lunchAmount);
                    merchantRet.setLunchPrice(lunchPrice);
                    // 外卖
                    merchantRet.setOderAmount(orderAmount);
                    merchantRet.setOderPrice(orderPrice);
                    // 晚餐
                    merchantRet.setDinnerAmount(dinnerAmount);
                    merchantRet.setDinnerPrice(dinnerPrice);
                    // 总消费
                    merchantRet.setTotalAmount(totalAmount);
                    merchantRet.setTotalPrice(totalPrice);
                    ret.add(merchantRet);
                }
            }
        }
    }
}
