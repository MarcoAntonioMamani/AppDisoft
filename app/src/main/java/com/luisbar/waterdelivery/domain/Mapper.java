package com.luisbar.waterdelivery.domain;

import com.luisbar.waterdelivery.domain.model.ClientModel;
import com.luisbar.waterdelivery.domain.model.OrderModel;
import com.luisbar.waterdelivery.domain.model.RequestDetail;
import com.luisbar.waterdelivery.domain.model.UserModel;
import com.luisbar.waterdelivery.presentation.model.InvoiceModel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Mapper {

    /**
     * It transform the UserModel from Presentation to the UserModel
     * in Domain
     * @param userModel UserModel from presentation layer
     * @return UserModel from domain layer
     */
    public UserModel userModelPToUserModelD(com.luisbar.waterdelivery.presentation.model.UserModel userModel) {
        return new UserModel(userModel.getCode(), userModel.getIdentityCard());
    }

    /**
     * It transform the InvoiceModel from Presentation to the InvoiceModel
     * in Domain
     * @param invoiceModel InvoiceModel from presentation layer
     * @return InvoiceModel from domain layer
     */
    public com.luisbar.waterdelivery.domain.model.InvoiceModel invoiceModelPToInvoiceModelD(InvoiceModel invoiceModel) {
        return new com.luisbar.waterdelivery.domain.model.InvoiceModel(invoiceModel.getId(), invoiceModel.getIdentityCard(),
            invoiceModel.getAmount(), invoiceModel.getClientName(), invoiceModel.getIdrepa(), invoiceModel.getProducts());//Aqui agregue invoiceModel.getIdrepa()
    }

    /**
     * It transforms the ClientModel from presentation to the ClientModel in Domain
     * @param clientModel ClientModel from presentation layer
     * @return ClientModel from domain layer
     */
    public ClientModel clientModelPToClientModelD(com.luisbar.waterdelivery.presentation.model.ClientModel clientModel) {
        return new ClientModel(clientModel.getName(), clientModel.getClientNit(), clientModel.getReason(),
                clientModel.getNit(), clientModel.getAddress(),
                clientModel.getPhone(), clientModel.getLocation());
    }

    /**
     * It transforms the OrderModel from presentation to the OrderModel in domain
     * @param orderModel OrderModel from presentation
     * @return OrderModel from domain layer
     */
    public OrderModel orderModelPToOrderModelD(com.luisbar.waterdelivery.presentation.model.OrderModel orderModel) {
        return new OrderModel(orderModel.getClientId(), orderModel.getDate(),
                orderModel.getObservation(), orderModel.getProducts(),
                orderModel.getTotal());
    }

    /**
     * It transform the list with native data to a xml
     * @param products List<List> object
     * @return String like xml
     */
    public String nativeListToXml(List<List> products) {
        String xml = null;

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("root");
            doc.appendChild(rootElement);

            for (List product : products) {
                Element row = doc.createElement("row");
                Element obcprod = doc.createElement("obcprod");
                Element obpcant = doc.createElement("obpcant");
                Element obpbase = doc.createElement("obpbase");
                Element obptot = doc.createElement("obptot");

                obcprod.appendChild(doc.createTextNode(product.get(4).toString()));
                obpcant.appendChild(doc.createTextNode(product.get(1).toString()));
                obpbase.appendChild(doc.createTextNode(product.get(2).toString()));
                obptot.appendChild(doc.createTextNode(product.get(3).toString()));

                row.appendChild(obcprod);
                row.appendChild(obpcant);
                row.appendChild(obpbase);
                row.appendChild(obptot);

                rootElement.appendChild(row);
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            StreamResult result = new StreamResult(new StringWriter());
            DOMSource source = new DOMSource(rootElement);
            transformer.transform(source, result);

            xml = result.getWriter().toString();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return xml;
    }
}
