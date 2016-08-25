package com.esperienza.intranetmall.mobile.validate;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.esperienza.intranetmall.mobile.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class Validate {
	private View layout;
	private Activity activity;
	private Map<Integer, Map<String,Object>> list;

	@SuppressLint("UseSparseArrays")
	public Validate(View layout){
		list  = new HashMap<Integer, Map<String,Object>>();
		this.layout = layout;
	}
	@SuppressLint("UseSparseArrays")
	public Validate(Activity activity){
		list  = new HashMap<Integer, Map<String,Object>>();
		this.activity = activity;
	}
	
	public boolean validateField(Integer idField){
		Map<String, Object> m = list.get(idField);
		if(m.get("view") instanceof EditText){
			EditText t = (EditText) m.get("view");
			if(t.getText().toString().equals("")){
				t.setError((String)m.get("requireMessage"));
				return false;
			}
			if(m.get("validateMethod") != null){
				Method exec = (Method) m.get("validateMethod");
				try {
					return (Boolean) exec.invoke(activity == null ? layout : activity);
					
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		}else 
		if(m.get("view") instanceof Spinner){
			Spinner s = (Spinner) m.get("view");
			if(s.getSelectedItemPosition() == 0){
				s.setBackgroundResource(R.drawable.spinner_oprigatorio_aviso);
				return false;
			}else{
				s.setBackgroundResource(R.drawable.spinner_oprigatorio);

			}
		}
		return true;
	}
	@SuppressWarnings("rawtypes")
	public boolean validate(){
		Boolean retorno  = true;
		for (Iterator iterator = list.entrySet().iterator(); iterator.hasNext();) {
			if(!validateField((Integer)((Map.Entry) iterator.next()).getKey())){
				retorno = false;
			}
			
		}
		return retorno;
		
	}
	public void addField(Integer idField, String MsgRequire, Method execValidate,Boolean setEvent ){
		
		Map<String,Object> dados = new HashMap<String,Object>();
		dados.put("validateMethod", execValidate);
		if(activity != null){
			dados.put("view", activity.findViewById(idField));
		}else{
			dados.put("view", layout.findViewById(idField));
			
		}
		dados.put("requireMessage", MsgRequire);
		if(setEvent)
			setEventField((View) dados.get("view"));
		list.put(idField, dados);
	}
	public void setEventField(View v){
		if(v instanceof EditText ){
			final EditText edt = (EditText) v;
			edt.addTextChangedListener(new TextWatcher(){
		        @Override
		        public void afterTextChanged(Editable arg0) {}
		        @Override
		        public void beforeTextChanged(CharSequence s, int start,  int count, int after) {}
		        @SuppressLint("InlinedApi")
				@Override
		        public void onTextChanged(CharSequence s, int start, int before, int count) {
		        	
		        	//edt.removeTextChangedListener(this);
		        	if(s.length() > 0){
		        		edt.setBackgroundResource(R.drawable.textfield_default_holo_light);
		        	}else{
		        		edt.setBackgroundResource(R.drawable.edit_text_obrigatorio);
		        	}
		        	//edt.addTextChangedListener(this);
		        }
		    });
			if(edt.length() > 0){
        		edt.setBackgroundResource(R.drawable.textfield_default_holo_light);
			}else{
        		edt.setBackgroundResource(R.drawable.edit_text_obrigatorio);
			}
		}
	}
	
}
