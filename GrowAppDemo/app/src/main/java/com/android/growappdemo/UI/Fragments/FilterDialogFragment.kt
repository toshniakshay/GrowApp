package com.android.growappdemo.UI.Fragments

import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.RadioGroup
import com.android.growappdemo.R


class FilterDialogFragment : DialogFragment(), View.OnClickListener {

    /**
     * Interface between caller and dialog for communicating state of filter
     */
    public interface FilterSelectionListener {
        fun onFilterSelected(selection: Int)
    }

    companion object {
        val CLASSTAG = FilterDialogFragment.javaClass.simpleName
        val SHOW_ALL : Int = 10001
        val SHOW_BY_DISC : Int = 10002
        val SHOW_BY_SALE : Int = 10003
    }

    var filerSelecrtion : FilterSelectionListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog : Dialog = super.onCreateDialog(savedInstanceState)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        return dialog

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view : View = inflater!!.inflate(R.layout.filter_popup, container, false);
        initView(view)
        return view
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.apply_filter -> {
                onApplyFilterClick()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    /**
     * Method to initialize UI components
     */
    private fun initView(view: View) {
        view.findViewById<Button>(R.id.apply_filter).setOnClickListener(this)
    }

    /**
     * On Apply button Tap get the selected option from radiogroup
     */
    fun onApplyFilterClick() {
        val RadioGroup = view.findViewById<RadioGroup>(R.id.filer_container)

        val checkedButton : Int = RadioGroup.checkedRadioButtonId

        when(checkedButton) {
            R.id.show_by_sale ->{
                notifyOptionSelected(SHOW_BY_SALE)
            }
            R.id.show_by_discount-> {
                notifyOptionSelected(SHOW_BY_DISC)
            }
        }

        dismiss()
    }

    /**
     * Notify caller about change in selection
     * */
    private fun notifyOptionSelected(selection : Int) {
        if (filerSelecrtion != null) {
            filerSelecrtion!!.onFilterSelected(selection)
        }
    }

}