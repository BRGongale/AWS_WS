package com.amazonaws.helloec2;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.client.apache.config.*;

import java.net.URI;

import org.apache.commons.httpclient.protocol.Protocol;

/**
 * @author a572393
 *
 */
public class Hello_EC2 {
	
	 public enum Type{
	        ami_id("ami-id"),
	        ami_launch_index("ami-launch-index"),
	        ami_manifest_path("ami-manifest-path"),
	        block_device_mapping("block-device-mapping/"),
	        hostname("hostname"),	
	        instance_action("instance-action"),
	        instance_id("instance-id"),
	        instance_type("instance-type"),
	        kernel_id("kernel-id"),
	        local_hostname("local-hostname"),
	        local_ipv4("local-ipv4"),
	        mac("mac"),
	        network("network/"),
	        placement("placement/"),
	        public_hostname("public-hostname"),
	        public_ipv4("public-ipv4"),
	        public_keys("public-keys/"),
	        reservation_id("reservation-id"),
	        security_groups("security-groups"),
	        services("services/");
	 
	        private String name;
	 
	        private Type(String name){
	            this.name = name;
	        }
	    }
	 public static String retrieveMetadata(Type instanceId, int timeout,String defaultValue) {
		 
		 try{
		 URI uri = URI.create("http://ec2-54-191-222-236.us-west-2.compute.amazonaws.com/latest/meta-data/" + instanceId);
		 System.out.println(uri.toString());
		 ClientConfig config = new DefaultApacheHttpClientConfig();
		// config.setProtocol(Protocol.HTTP);
		// config.setProxyHost("proxy.com");
		 //config.setProxyPort(1234);

		 config.getProperties().put(ClientConfig.PROPERTY_CONNECT_TIMEOUT,timeout);
		 Client client = Client.create(config);
         WebResource webResource = client.resource(uri);
		 
         ClientResponse response = webResource.get(ClientResponse.class);
         
         String results = response.getEntity(String.class);
         return results;
		 }catch(Throwable t ){
			 t.printStackTrace();
			 return defaultValue;
		 }
		 
	 }
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Hello_EC2 ec2 = new Hello_EC2();
		 String myEC2Id = retrieveMetadata(Type.instance_id,5000, null);
	        System.out.println("The Instance Id is " + myEC2Id + " .");
	}

}