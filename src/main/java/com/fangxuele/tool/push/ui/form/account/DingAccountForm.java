package com.fangxuele.tool.push.ui.form.account;

import cn.hutool.cache.impl.TimedCache;
import cn.hutool.json.JSONUtil;
import com.dingtalk.api.DefaultDingTalkClient;
import com.fangxuele.tool.push.App;
import com.fangxuele.tool.push.bean.account.DingAccountConfig;
import com.fangxuele.tool.push.domain.TAccount;
import com.fangxuele.tool.push.logic.msgsender.DingMsgSender;
import com.fangxuele.tool.push.ui.form.MainWindow;
import com.fangxuele.tool.push.util.SqliteUtil;
import com.fangxuele.tool.push.util.UIUtil;
import com.fangxuele.tool.push.util.UndoUtil;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;

@Getter
@Slf4j
public class DingAccountForm implements IAccountForm {
    private JPanel mainPanel;
    private JTextField appSecretTextField;
    private JTextField appNameTextField;
    private JTextField agentIdTextField;
    private JTextField appKeyTextField;

    private static DingAccountForm wxMpAccountForm;

    public volatile static DefaultDingTalkClient defaultDingTalkClient;
    public volatile static DefaultDingTalkClient robotClient;
    public static TimedCache<String, String> accessTokenTimedCache;

    @Override
    public void init(String accountName) {
        if (StringUtils.isNotEmpty(accountName)) {
            TAccount tAccount = accountMapper.selectByMsgTypeAndAccountName(App.config.getMsgType(), accountName);

            DingAccountForm instance = getInstance();
            DingAccountConfig dingAccountConfig = JSONUtil.toBean(tAccount.getAccountConfig(), DingAccountConfig.class);
            instance.getAppSecretTextField().setText(dingAccountConfig.getAppSecret());
            instance.getAppNameTextField().setText(dingAccountConfig.getAppName());
            instance.getAgentIdTextField().setText(dingAccountConfig.getAgentId());
            instance.getAppKeyTextField().setText(dingAccountConfig.getAppKey());
        }
    }

    @Override
    public void save(String accountName) {
        if (StringUtils.isNotEmpty(accountName)) {
            TAccount tAccount = accountMapper.selectByMsgTypeAndAccountName(App.config.getMsgType(), accountName);
            DingAccountForm instance = getInstance();
            int msgType = App.config.getMsgType();

            boolean existSameAccount = false;

            if (tAccount != null) {
                existSameAccount = true;
            }

            int isCover = JOptionPane.NO_OPTION;
            if (existSameAccount) {
                // 如果存在，是否覆盖
                isCover = JOptionPane.showConfirmDialog(MainWindow.getInstance().getMessagePanel(), "已经存在同名的账号，\n是否覆盖？", "确认",
                        JOptionPane.YES_NO_OPTION);
            }

            if (!existSameAccount || isCover == JOptionPane.YES_OPTION) {

                String now = SqliteUtil.nowDateForSqlite();

                TAccount tAccount1 = new TAccount();
                tAccount1.setMsgType(msgType);
                tAccount1.setAccountName(accountName);

                DingAccountConfig dingAccountConfig = new DingAccountConfig();
                dingAccountConfig.setAppSecret(instance.getAppSecretTextField().getText());
                dingAccountConfig.setAppName(instance.getAppNameTextField().getText());
                dingAccountConfig.setAgentId(instance.getAgentIdTextField().getText());
                dingAccountConfig.setAppKey(instance.getAppKeyTextField().getText());

                tAccount1.setAccountConfig(JSONUtil.toJsonStr(dingAccountConfig));

                tAccount1.setModifiedTime(now);

                if (existSameAccount) {
                    accountMapper.updateByMsgTypeAndAccountName(tAccount1);
                } else {
                    tAccount1.setCreateTime(now);
                    accountMapper.insertSelective(tAccount1);
                }

                JOptionPane.showMessageDialog(MainWindow.getInstance().getMainPanel(), "保存成功！", "成功",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }

    @Override
    public void clear() {
        UIUtil.clearForm(getInstance());
    }

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }

    public static DingAccountForm getInstance() {
        if (wxMpAccountForm == null) {
            wxMpAccountForm = new DingAccountForm();
        }
        UndoUtil.register(wxMpAccountForm);
        return wxMpAccountForm;
    }

    public static TimedCache<String, String> getAccessTokenTimedCache(String accountName) {
        invalidAccount();

        TAccount tAccount = accountMapper.selectByMsgTypeAndAccountName(App.config.getMsgType(), accountName);
        if (tAccount == null) {
            log.error("未获取到对应的微信公众号账号配置:{}", accountName);
        }

        return DingMsgSender.getAccessTokenTimedCache(tAccount.getId());
    }

    public static void invalidAccount() {
        accessTokenTimedCache = null;
        defaultDingTalkClient = null;
        robotClient = null;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(1, 1, new Insets(10, 5, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(5, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("应用名称");
        panel1.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("AgentId");
        panel1.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Appkey");
        panel1.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("AppSecret");
        panel1.add(label4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        appNameTextField = new JTextField();
        panel1.add(appNameTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        agentIdTextField = new JTextField();
        panel1.add(agentIdTextField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        appKeyTextField = new JTextField();
        panel1.add(appKeyTextField, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        appSecretTextField = new JTextField();
        panel1.add(appSecretTextField, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}
