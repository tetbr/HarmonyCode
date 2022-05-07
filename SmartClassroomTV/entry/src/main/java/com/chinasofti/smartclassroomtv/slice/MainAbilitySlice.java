package com.chinasofti.smartclassroomtv.slice;


import com.alibaba.fastjson.JSON;
import com.chinasofti.smartclassroomtv.ResourceTable;
import com.chinasofti.smartclassroomtv.data.ShowQuestionProvider;
import com.chinasofti.smartclassroomtv.data.ResultData;
import com.chinasofti.smartclassroomtv.demain.Question;
import com.chinasofti.smartclassroomtv.utils.HttpClient;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Button;
import ohos.agp.components.ListContainer;

import java.util.ArrayList;
import java.util.List;

public class MainAbilitySlice extends AbilitySlice {
    private ListContainer mListContainer;
    ShowQuestionProvider itemProvider;
    private List<Question> mListInfo=new ArrayList<>();
    Button btnUpdate;
    Button btnExite;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        setUIContent(ResourceTable.Layout_main);

        initSliceLayout();  //UI 组件初始化
        update();
    }


    private void initSliceLayout() {
        mListContainer = (ListContainer) findComponentById(ResourceTable.Id_list_container);
        btnUpdate = (Button) findComponentById(ResourceTable.Id_btn_update);
        btnExite = (Button) findComponentById(ResourceTable.Id_btn_exit);

        mListContainer.setItemClickedListener((listContainer, component, i, l) -> {
            //Ability跳转
            Question question = itemProvider.getItem(i);
            Operation operation = new Intent.OperationBuilder().
                    withBundleName("com.chinasofti.smartclassroomtv")
                    .withAbilityName("com.chinasofti.smartclassroomtv.ShowQuestionAbility") //目标Ability
                    .build();
            Intent intent1 = new Intent();
            intent1.setOperation(operation);
//            intent1.setParam("index", i);
            intent1.setParam("question",question);
            startAbility(intent1);

        });
        btnUpdate.setClickedListener(component -> update());
        btnExite.setClickedListener(listener -> terminateAbility());
    }


    void update() {
        new Thread(() -> {//在非线程
            String res = HttpClient.doGet();
            ResultData resultData = JSON.parseObject(res, ResultData.class);
            if (resultData == null) {
                return;
            }
            mListInfo= resultData.getData();

            getUITaskDispatcher().asyncDispatch(() -> {//获取UI 线程，也是主线
                itemProvider = new ShowQuestionProvider(mListInfo, this);
                mListContainer.setItemProvider(itemProvider);
            });
        }).start();
    }

}
