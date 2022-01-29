package com.example.video_call_webex.webexcall.participants

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciscowebex.androidsdk.phone.CallMembership
import com.example.video_call_webex.R
import com.example.video_call_webex.databinding.FragmentParticipantsBinding
import com.example.video_call_webex.utils.showDialogWithMessage
import com.example.video_call_webex.viewmodel.WebexViewModel
import com.example.video_call_webex.webexcall.CallActivity

/*
* @ParticipantsFragment is a dialog fragment used to show the participants
*/
class ParticipantsFragment : DialogFragment(), ParticipantsAdapter.OnItemActionListener {

    lateinit var binding: FragmentParticipantsBinding
    lateinit var adapter: ParticipantsAdapter
    private lateinit var webexViewModel: WebexViewModel
    private var currentCallId: String? = null
    private var selfId: String? = null

    companion object {
        private const val CALL_KEY = "call_id"
        private const val SELF_ID_KEY = "self_id"

        fun newInstance(callid: String): ParticipantsFragment {
            val bundle = Bundle()
            bundle.putString(CALL_KEY, callid)
            val fragment = ParticipantsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val participantsBinding = DataBindingUtil.inflate<FragmentParticipantsBinding>(
            LayoutInflater.from(context),
            R.layout.fragment_participants,
            container,
            false
        ).also { binding = it }.apply {
            webexViewModel = (activity as? CallActivity)?.webexViewModel!!
            Log.d(tag, "onCreateView webexViewModel: $webexViewModel")
            selfId = webexViewModel.selfPersonId
            setUpViews()
        }
        return participantsBinding.root
    }

    private fun setUpViews() {
        adapter = ParticipantsAdapter(arrayListOf(), this, selfId.orEmpty())
        binding.participants.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(
            requireContext(),
            LinearLayoutManager.VERTICAL
        )
        binding.participants.addItemDecoration(dividerItemDecoration)

        webexViewModel.currentCallId?.let { _callId ->
            currentCallId = _callId
            webexViewModel.getParticipants(_callId)
        }

        webexViewModel.callMembershipsLiveData.observe(this, Observer {
            it?.let { callMemberships ->
                Log.d(tag, callMemberships.toString())
                val data = arrayListOf<Any>()
                val stateWiseMap = callMemberships.groupBy { it.getState() }
                stateWiseMap.keys.forEach { state ->
                    val memberships = stateWiseMap[state]
                    data.add(webexViewModel.getHeader(state))
                    data.addAll(memberships.orEmpty())
                }
                adapter.refreshData(data)
                adapter.notifyDataSetChanged()
            }
        })

        binding.close.setOnClickListener { dismiss() }

    }

    fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onParticipantMuted(participantId: String) {
        currentCallId?.let {
            if (webexViewModel.getCall(webexViewModel.currentCallId.orEmpty())
                    ?.isCUCMCall() == false || webexViewModel.selfPersonId == participantId
            ) {
                webexViewModel.muteParticipant(it, participantId)
                adapter.notifyDataSetChanged()
            } else {
                showToast(getString(R.string.mute_feature_is_not_available_for_cucm_calls))
            }
        }
    }

    override fun onLetInClicked(callMembership: CallMembership) {
        if (callMembership.getState() == CallMembership.State.WAITING) {
            context?.let { ctx ->
                showDialogWithMessage(ctx,
                    getString(R.string.message),
                    getString(R.string.let_in_confirmation),
                    onPositiveButtonClick = { dialog, _ ->
                        currentCallId?.let {
                            webexViewModel.letIn(it, callMembership)
                        }
                        dialog.dismiss()
                    },
                    onNegativeButtonClick = { dialog, _ ->
                        dialog.dismiss()
                    })
            }
        }
    }
}