package com.atguigu.prototype.deepclone;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 深度拷贝的实现拷贝的两种方式
 * @author Administrator
 *
 */
public class DeepPrototype implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String name;

	public DeepCloneableTarget deepCloneableTarget;

	public DeepPrototype() {

	}

	// 方式1 ：通过重写clone方法来实现深拷贝 （引用对象多，这种方法比较繁琐）
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Object deep = null;
		try {
			deep = super.clone();//这里完成对基本数据类型的克隆
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		DeepPrototype deepPrototype = (DeepPrototype) deep;
		//对引用类型，需要单独处理
		deepPrototype.deepCloneableTarget = (DeepCloneableTarget) deepPrototype.deepCloneableTarget.clone();
		return deepPrototype;
	}

	// 方式2： 通过对象序列化实现深拷贝 （推荐）
	public Object deepClone() {
		 //深度克隆
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            //return super.clone();//默认浅克隆，只克隆八大基本数据类型和String
            //序列化
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            
            //反序列化
            bis = new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bis);
            DeepPrototype copy = (DeepPrototype)ois.readObject();
            return copy;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally{
            try {
                bos.close();
                oos.close();
                bis.close();
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
}
