package com.luisbar.waterdelivery.presentation.view.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.luisbar.waterdelivery.R;
import com.luisbar.waterdelivery.common.config.Config;
import com.luisbar.waterdelivery.data.Utilitario.GlobalUtil;
import com.luisbar.waterdelivery.data.Utilitario.producto;
import com.luisbar.waterdelivery.presentation.view.adapter.ProductAutoCompleteAdapter;

import java.io.Serializable;
import java.util.List;

public class ProductDialogFragment extends DialogFragment implements AdapterView.OnItemClickListener{

    private int code;
    private String name;
    private Double price;
    private View view;

    /**
     * Create a new instance of ConfirmationDialogFragment
     */
    public static ProductDialogFragment newInstance(String title, Fragment fragment, List lstProduct) {
        ProductDialogFragment productDialogFragment = new ProductDialogFragment();
        Bundle args = new Bundle();

        args.putInt(Config.FRAGMENT, fragment.getId());
        args.putString(Config.TITLE, title);
        args.putSerializable(Config.LIST, (Serializable) lstProduct);

        productDialogFragment.setArguments(args);
        productDialogFragment.setCancelable(false);

        return productDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        final Bundle args = getArguments();
        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.fragment_dialog_product, null);
        setAutoComplete(view, args);

        builder
        .setCancelable(false)
        .setView(view)
        .setTitle(args.getString(Config.TITLE))
        .setPositiveButton(R.string.txt_49,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        ProductDialogFragmentI productDialogFragmentI;
                        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(args.getInt(Config.FRAGMENT));
                        EditText etQuantity = (EditText) view.findViewById(R.id.input_dialog_quantity);



                        producto p=null;
                        int i =0; boolean b =false;
                        while (i<GlobalUtil.listProducto.size()&& b==false){
                            producto aux =GlobalUtil.listProducto.get(i);
                            if (aux.getId()==getCode()){
                                b=true;
                                p=aux;
                            }
                            i++;
                        }
                        if(p.getCant()>=0 ){
                            if(Double.valueOf(etQuantity.getText().toString())>p.getCant()){
                                Toast.makeText(getContext(),"Error: Cantidad No Existente....",Toast.LENGTH_LONG).show();
                                return;
                            }
                        }

                        productDialogFragmentI = (ProductDialogFragmentI) fragment;
                        productDialogFragmentI.accept(getCode(),
                                getName(),
                                getPrice(),
                                etQuantity.getText().toString().isEmpty()
                                ? 0
                                : Double.valueOf(etQuantity.getText().toString()));
                    }
                }
        )
        .setNegativeButton(R.string.txt_50,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        ProductDialogFragmentI productDialogFragmentI;
                        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(args.getInt(Config.FRAGMENT));

                        productDialogFragmentI = (ProductDialogFragmentI) fragment;
                        productDialogFragmentI.cancelProduct();
                    }
                }
        );

        return builder.create();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }




    //////////Aqui se Carga El precio del producto
    private void setAutoComplete(View view, Bundle args) {
        AutoCompleteTextView inputProduct = (AutoCompleteTextView) view.findViewById(R.id.input_dialog_product);

        ArrayAdapter<ProductAutoCompleteAdapter.Product> adapter =
                new ArrayAdapter(getActivity(),
                        android.R.layout.simple_list_item_1,
                        new ProductAutoCompleteAdapter((List) args.getSerializable(Config.LIST)));

        inputProduct.setAdapter(adapter);
        inputProduct.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ProductAutoCompleteAdapter.Product product = (ProductAutoCompleteAdapter.Product) parent.getAdapter().getItem(position);
        setCode(product.getId());
        setName(product.getName());
        setPrice(Double.valueOf(product.getPrice()));

       // setPrice(1500.50);
    }

    /**
     * Interface for triggering an event when the accept or cancel button has been pressed
     */
    public interface ProductDialogFragmentI {
        void accept(int code, String name, Double price, Double quantity);
        void cancelProduct();
    }
}
