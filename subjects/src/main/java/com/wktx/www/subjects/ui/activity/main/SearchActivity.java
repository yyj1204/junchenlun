package com.wktx.www.subjects.ui.activity.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.login.AccountInfoData;
import com.wktx.www.subjects.apiresult.main.CompanyListInfoData;
import com.wktx.www.subjects.apiresult.main.position.PositionListInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.main.SearchPresenter;
import com.wktx.www.subjects.ui.activity.message.CompanyRecruitActivity;
import com.wktx.www.subjects.ui.adapter.main.CompanyAdapter;
import com.wktx.www.subjects.ui.adapter.main.PositionAdapter;
import com.wktx.www.subjects.ui.view.main.ISearchView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.utils.String2ListUtil;
import com.wktx.www.subjects.widget.MyLayoutManager;
import com.wktx.www.subjects.utils.ToastUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 搜索
 */
public class SearchActivity extends ABaseActivity<ISearchView,SearchPresenter> implements ISearchView {
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    //搜索类别（职位、公司）
    @BindView(R.id.tv_position)
    TextView tvPosition;
    @BindView(R.id.tv_positionLine)
    TextView tvPositionLine;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_companyLine)
    TextView tvCompanyLine;
    //历史搜索记录
    @BindView(R.id.linear_history)
    LinearLayout llHistory;
    @BindView(R.id.tagflow_history)
    TagFlowLayout tagflowHistory;
    @BindView(R.id.tv_delete)
    TextView tvDelete;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private List<String> historyList;//历史搜索记录的集合
    private String historystrs;//历史搜索记录的字符串

    //RecyclerView 适配器
    private PositionAdapter mPositionAdapter;//职位招聘列表适配器
    private CompanyAdapter mCompanyAdapter;//公司列表适配器

    private boolean isSearchPosition;//true:职位 false:公司

    private int page = 1;
    private static final int PAGE_SIZE = 10;//请求每页的数据量
    private boolean isRefresh;//是下拉刷新还是加载更多  false：加载更多，true：下拉刷新

    @OnClick({R.id.tb_IvReturn,R.id.rela_position,R.id.rela_company,R.id.tv_search,R.id.tv_delete})
    public void MyOnclick(View view) {
        //将输入法隐藏
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(llHistory.getWindowToken(), 0);
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.rela_position://搜索职位
                setSearchState(true);
                recyclerView.setAdapter(mPositionAdapter);
                break;
            case R.id.rela_company://搜索公司
                setSearchState(false);
                recyclerView.setAdapter(mCompanyAdapter);
                break;
            case R.id.tv_search:
                if (MyUtils.isFastClick()){
                    return;
                }

                if (TextUtils.isEmpty(getKeyStrStr())) {
                    ToastUtil.myToast("请输入关键字进行搜索！");
                    etSearch.requestFocus();
                } else {//遍历本地历史搜索记录，判断是否已存在该关键字
                    for (String str : historyList) {
                        //已存在，将字符串集合重新排序后保存在本地
                        if (str.equals(getKeyStrStr())) {
                            historyList.remove(str);
                            break;
                        } else {//不存在，只保留15条
                            if (historyList.size() >= 15) {
                                historyList.remove(historyList.size() - 1);
                                break;
                            }
                        }
                    }
                    historyList.add(0, getKeyStrStr());
                    try {
                        historystrs = String2ListUtil.SceneList2String(historyList);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    editor.putString("searchhistorystrStaff", historystrs);
                    editor.commit();
                    refresh();
                }
                break;
            case R.id.tv_delete:
                llHistory.setBackgroundResource(R.drawable.img_nothing);
                tvDelete.setVisibility(View.GONE);
                tagflowHistory.setVisibility(View.GONE);
                historyList.clear();
                editor.clear();
                editor.commit();
                ToastUtil.myToast("历史搜索记录清除成功!");
                break;
            default:
                break;
        }
    }

    /**
     * 设置搜索（职位、公司）显示相关布局
     * @param isPosition
     */
    private void setSearchState(boolean isPosition) {
        isSearchPosition=isPosition;
        tvPosition.setSelected(isPosition);
        tvCompany.setSelected(!isPosition);
        if (isPosition){//职位
            etSearch.setHint("搜索职位");
            tvPositionLine.setVisibility(View.VISIBLE);
            tvCompanyLine.setVisibility(View.INVISIBLE);
        }else {//公司
            etSearch.setHint("搜索公司");
            tvPositionLine.setVisibility(View.INVISIBLE);
            tvCompanyLine.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("SearchHistoryStaff", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        setSearchState(true);
        initData();
        initUI();
        initRecycleView();
        initAdapter();
        initRefreshLayout();
    }

    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter();
    }

    /**
     * 获取本地保存的历史搜索记录
     * 将字符串转换成字符串集合
     */
    private void initData() {
        historystrs = sharedPreferences.getString("searchhistorystrStaff", "");
        historyList = new ArrayList<>();
        if (!TextUtils.isEmpty(historystrs)) {
            try {
                historyList = String2ListUtil.String2SceneList(historystrs);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void initUI() {
        swipeRefreshLayout.setVisibility(View.GONE);
        llHistory.setVisibility(View.VISIBLE);
        //本地历史搜索记录为空，不显示删除按钮
        if (historyList.size() == 0 || historyList == null) {
            llHistory.setBackgroundResource(R.drawable.img_nothing);
            tvDelete.setVisibility(View.GONE);
            tagflowHistory.setVisibility(View.GONE);
            ToastUtil.myToast("暂无任何历史搜索");
        } else {//不为空，显示出来
            initFlowLayout();
            llHistory.setBackgroundResource(R.color.color_f0f0f0);
            tvDelete.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化流式布局
     */
    private void initFlowLayout() {
        //tag赋值
        TagAdapter<String> tagAdapter = new TagAdapter<String>(historyList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.item_flowlayout_search, tagflowHistory, false);
                tv.setTextSize(12);
                tv.setText(s);
                return tv;
            }
        };

        tagflowHistory.setAdapter(tagAdapter);
        //tag点击事件
        tagflowHistory.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                return true;
            }
        });
        //设置监听事件的回调
        tagflowHistory.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                //截取选中位置([1])
                String s = selectPosSet.toString();
                String substring = s.substring(1, s.length()-1);
                //选中搜索换关键字（将字符串集合重新排序后保存在本地）
                String search = historyList.get(Integer.parseInt(substring));
                historyList.remove(search);
                historyList.add(0, search);
                try {
                    historystrs = String2ListUtil.SceneList2String(historyList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                editor.putString("searchhistorystrStaff", historystrs);
                editor.commit();
                //进行搜索请求
                etSearch.setText(search);
                refresh();

            }
        });
    }

    /**
     * 初始化筛选结果 RecycleView
     */
    private void initRecycleView() {
        //设置刷新圈圈的颜色
        swipeRefreshLayout.setColorSchemeColors(Color.rgb(255, 179, 33));
        swipeRefreshLayout.setRefreshing(true);
        //设置分割线与垂直方向布局
        recyclerView.addItemDecoration(MyUtils.drawDivider(SearchActivity.this, LinearLayout.VERTICAL, R.drawable.divider_f0f0f0_14));
        MyLayoutManager manager = new MyLayoutManager(SearchActivity.this, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
    }

    /**
     * 为 RecyclerView 加载 Adapter
     */
    private void initAdapter() {
        mPositionAdapter = new PositionAdapter(SearchActivity.this);
        mCompanyAdapter = new CompanyAdapter(SearchActivity.this);
        mPositionAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });
        mPositionAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });
        recyclerView.setAdapter(mPositionAdapter);
        //子控件点击事件
        mPositionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (MyUtils.isFastClick1()){
                    return;
                }
                //打开招聘详情界面（传递招聘需求id）
                Intent intent = new Intent(SearchActivity.this, PositionDetailsActivity.class);
                intent.putExtra(ConstantUtil.KEY_DATA,mPositionAdapter.getData().get(position).getId());
                startActivity(intent);
            }
        });
        //子控件点击事件
        mCompanyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (MyUtils.isFastClick1()){
                    return;
                }
                //打开公司职位招聘列表界面（传递公司id）
                Intent intent = new Intent(SearchActivity.this, CompanyRecruitActivity.class);
                intent.putExtra(ConstantUtil.KEY_DATA,mCompanyAdapter.getData().get(position).getUser_id());
                startActivity(intent);
            }
        });
    }

    /**
     * 下拉刷新 ---  下拉加载更多
     */
    private void initRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    //刷新
    private void refresh() {
        llHistory.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        page = 1;
        isRefresh=true;
        if (isSearchPosition){
            //这里的作用是防止下拉刷新的时候还可以上拉加载
            mPositionAdapter.setEnableLoadMore(false);
            getPresenter().getPositionInfo(page);
        }else {
            //这里的作用是防止下拉刷新的时候还可以上拉加载
            mCompanyAdapter.setEnableLoadMore(false);
            getPresenter().getCompanyInfo(page);
        }
    }
    //加载更多
    private void loadMore() {
        isRefresh=false;
        if (isSearchPosition){
            getPresenter().getPositionInfo(page);
        }else {
            getPresenter().getCompanyInfo(page);
        }
    }


    /**
     * ISearchView
     */
    @Override
    public AccountInfoData getUserInfo() {
        return null;
    }
    @Override
    public String getKeyStrStr() {
        return etSearch.getText().toString().trim();
    }
    @Override//获取公司搜索列表回调
    public void onGetCompanySuccessResult(List<CompanyListInfoData> result) {
        recyclerView.setBackgroundResource(R.color.color_f0f0f0);
        setCompanyData(result);
        if (isRefresh){//停止刷新
            mCompanyAdapter.setEnableLoadMore(true);
            swipeRefreshLayout.setRefreshing(false);
        }
    }
    @Override
    public void onGetCompanyFailureResult(String result) {
        String toastStr=result;
        setPositionData(null);
        //如果是刷新，没数据代表全部没数据
        if (isRefresh){
            if (result.equals("")){
                toastStr="暂无任何搜索结果！";
                recyclerView.setBackgroundResource(R.drawable.img_nothing);
            }else {
                recyclerView.setBackgroundResource(R.drawable.img_nothing_net);
            }
            mCompanyAdapter.setEnableLoadMore(true);
            swipeRefreshLayout.setRefreshing(false);
        }else {//如果是加载，没数据代表加载完毕
            if (result.equals("")){//没数据
                toastStr="已经到底了哦！";
                mCompanyAdapter.loadMoreEnd();
            }else {//请求出错
                mCompanyAdapter.loadMoreFail();
            }
        }
        ToastUtil.myToast(toastStr);
    }
    @Override//获取职位招聘搜索列表回调
    public void onRequestSuccess(List<PositionListInfoData> tData) {
        recyclerView.setBackgroundResource(R.color.color_f0f0f0);
        setPositionData(tData);
        if (isRefresh){//停止刷新
            mPositionAdapter.setEnableLoadMore(true);
            swipeRefreshLayout.setRefreshing(false);
        }
    }
    @Override
    public void onRequestFailure(String result) {
        String toastStr=result;
        setPositionData(null);
        //如果是刷新，没数据代表全部没数据
        if (isRefresh){
            if (result.equals("")){
                toastStr="暂无任何搜索结果！";
                recyclerView.setBackgroundResource(R.drawable.img_nothing);
            }else {
                recyclerView.setBackgroundResource(R.drawable.img_nothing_net);
            }
            mPositionAdapter.setEnableLoadMore(true);
            swipeRefreshLayout.setRefreshing(false);
        }else {//如果是加载，没数据代表加载完毕
            if (result.equals("")){//没数据
                toastStr="已经到底了哦！";
                mPositionAdapter.loadMoreEnd();
            }else {//请求出错
                mPositionAdapter.loadMoreFail();
            }
        }
        ToastUtil.myToast(toastStr);
    }

    /**
     * 设置职位招聘列表数据
     */
    private void setPositionData(List data) {
        page++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mPositionAdapter.setNewData(data);
        } else {
            if (size > 0) {
                mPositionAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mPositionAdapter.loadMoreEnd(isRefresh);
        } else {
            mPositionAdapter.loadMoreComplete();
        }
    }

    /**
     * 设置公司列表数据
     */
    private void setCompanyData(List data) {
        page++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mCompanyAdapter.setNewData(data);
        } else {
            if (size > 0) {
                mCompanyAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mCompanyAdapter.loadMoreEnd(isRefresh);
        } else {
            mCompanyAdapter.loadMoreComplete();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
