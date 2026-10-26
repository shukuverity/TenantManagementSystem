package com.example.tenantmanagementsys

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.tenantmanagementsys.databinding.FragmentViewTenantsBinding

class ViewTenantsFragment : Fragment() {

    private var _binding: FragmentViewTenantsBinding? = null
    private val binding get() = _binding!!

    // Receives the optional tenant passed from AddTenantFragment.
    private val args: ViewTenantsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewTenantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newTenant = args.newTenant
        binding.tenant = newTenant

        // Hide the "Just added" section if this screen was opened without a tenant.
        if (newTenant == null) {
            binding.newTenantGroup.visibility = View.GONE
        }

        val tenants = TenantRepository.tenants
        binding.allTenantsTextView.text = if (tenants.isEmpty()) {
            "No tenants yet. Add one from the Dashboard."
        } else {
            tenants.joinToString("\n\n") { it.summary() }
        }

        binding.callButton.setOnClickListener {
            if (newTenant != null) {
                val dialIntent = Intent(
                    Intent.ACTION_DIAL,
                    Uri.parse("tel:${newTenant.phone}")
                )
                startActivity(dialIntent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}