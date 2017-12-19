package aemlearning.pojo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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

		JsonObject jObj;
		JsonArray jNestedArr;
		Gson gson = new Gson();
		try {
			String[] itemsProps = getProperties().get("myUserSubmenu", String[].class);
			if (itemsProps != null) {
				for (int i = 0; i < itemsProps.length; i++) {
					JsonElement json = gson.fromJson(itemsProps[i], JsonElement.class);
					jObj = json.getAsJsonObject();
					TouchNestedMultiFieldBean menuItem = new TouchNestedMultiFieldBean();
					String title = jObj.get("title").toString();
					String path = jObj.get("link").toString();
					jNestedArr = jObj.getAsJsonArray("myNestedUserSubmenu");
					if (!jNestedArr.isJsonNull() && jNestedArr.size() > 0) {
						JsonObject jNestedObj = jNestedArr.get(0).getAsJsonObject();
						menuItem.setSubtitle(jNestedObj.get("subtitle").toString());
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