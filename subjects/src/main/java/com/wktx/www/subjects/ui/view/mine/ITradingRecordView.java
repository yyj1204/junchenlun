package com.wktx.www.subjects.ui.view.mine;
import com.wktx.www.subjects.apiresult.mine.tradingrecord.TradingBalanceInfoData;
import com.wktx.www.subjects.apiresult.mine.tradingrecord.TradingRecordInfoData;
import com.wktx.www.subjects.ui.view.IView;

import java.util.List;

/**
 * Created by yyj on 2018/5/8.
 * 交易记录
 */

public interface ITradingRecordView extends IView<List<TradingRecordInfoData>> {

    /**
     * 获取账户余额与托管资金成功的回调
     */
    void onGetBalanceSuccessResult(TradingBalanceInfoData result);

    /**
     * 获取账户余额与托管资金失败的回调
     */
    void onGetBalanceFailureResult(String result);
}
