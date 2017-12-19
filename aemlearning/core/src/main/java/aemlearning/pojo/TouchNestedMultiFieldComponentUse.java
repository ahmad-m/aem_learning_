package aemlearning.pojo;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;

import aemlearning.bean.TouchNestedMultiFieldBean;

public class TouchNestedMultiFieldComponentUse extends WCMUsePojo {

	private static final Logger LOGGER = LoggerFactory.getLogger(TouchNestedMultiFieldComponentUse.class);
	private List<TouchNestedMultiFieldBean> submenuItems = new ArrayList<>();

	@Override
	public void activate() throws Exception {
		setNestedMultiFieldItems();
	}

	/**
	 * Method to get Multi field data
	 * 
	 * @return submenuItems
	 */
	private List<TouchNestedMultiFieldBean> setNestedMultiFieldItems() {

		@SuppressWarnings("deprecation")
		JSONObject jObj;
		JSONArray jNestedArr;
		try {
			String[] itemsProps = getProperties().get("myUserSubmenu", String[].class);
			if (itemsProps != null) {
				for (int i = 0; i < itemsProps.length; i++) {

					jObj = new JSONObject(itemsProps[i]);
					TouchNestedMultiFieldBean menuItem = new TouchNestedMultiFieldBean();

					String title = jObj.getString("title");
					String path = jObj.getString("link");
					jNestedArr = jObj.getJSONArray("myNestedUserSubmenu");
					if (jNestedArr != null && jNestedArr.length() > 0) {
						JSONObject jNestedObj = jNestedArr.getJSONObject(0);
						menuItem.setSubtitle(jNestedObj.getString("subtitle"));
					}
					menuItem.setTitle(title);
					menuItem.setLink(path);
					submenuItems.add(menuItem);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception while Multifield data {}", e.getMessage(), e);
		}
		return submenuItems;
	}

	public List<TouchNestedMultiFieldBean> getMultiFieldItems() {
		return submenuItems;
	}
}