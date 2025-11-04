package com.example.testapp.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.testapp.R // Убедитесь, что у вас есть этот id в nav_graph
import com.example.testapp.databinding.FragmentAuthBinding // Сгенерированный класс
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.core.net.toUri

@AndroidEntryPoint
class AuthFragment : Fragment() {
    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        observeFlows()
    }

    private fun setupListeners() {
        setupTextWatchers()
        setupLoginButton()
        setupVkButton()
        setupOkButton()
    }

    private fun setupTextWatchers() {
        binding.etEmail.doOnTextChanged { text, _, _, _ ->
            viewModel._email.value = text.toString()
        }

        binding.etPassword.doOnTextChanged { text, _, _, _ ->
            viewModel._password.value = text.toString()
        }
    }

    private fun setupLoginButton() {
        binding.btnLogin.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.signIn()
            }
        }
    }

    private fun setupVkButton() {
        binding.btnVK.setOnClickListener {
            openBrowser("https://vk.com/")
        }
    }

    private fun setupOkButton() {
        binding.btnOk.setOnClickListener {
            openBrowser("https://ok.ru/")
        }
    }

    private fun openBrowser(url: String) {
        val intent = android.content.Intent(android.content.Intent.ACTION_VIEW).apply {
            data = url.toUri()
        }
        startActivity(intent)
    }

    private fun observeFlows() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { observeAction() }
            }
        }
    }

    private suspend fun observeAction() {
        viewModel.actions.collect { action ->
            when (action) {
                AuthViewModel.Action.SignIn -> {
                   navigateToHome()
                }
            }
        }
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_authFragment_to_homeFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
