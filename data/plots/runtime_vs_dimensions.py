import matplotlib.pyplot as plt


# dimensions
dimensions = [2, 4, 8]


uni_skyline= [7.41, 14.23, 196.72]  # Skyline query times
uni_top_k_dominating = [36.04, 948.28, 13109.32]  # Top-k dominating
uni_top_k_skyline = [8.99, 67.15, 928.30]  # Top-k skyline

norm_skyline = [7.37, 17.11, 169.16]  # Skyline query times
norm_top_k_dominating = [43.89, 976.58, 9656.78]  # Top-k dominating
norm_top_k_skyline = [11.52, 71.88, 710.77]  # Top-k skyline


# plot options
xlabel = "Dimensions"
ylabel = "Execution time [s]"

# plot execution times for uniform
plt.figure()
plt.title("Uniform distribution")
plt.xlabel(xlabel)
plt.ylabel(ylabel)
plt.ylim(top=14000)
plt.plot(dimensions, uni_skyline)
plt.plot(dimensions, uni_top_k_dominating)
plt.plot(dimensions, uni_top_k_skyline)
plt.legend(["Skyline", "Top-k dominating", "Top-k skyline"])

# plot execution times for normal
plt.figure()
plt.title("Normal distribution")
plt.xlabel(xlabel)
plt.ylabel(ylabel)
plt.ylim(top=14000)
plt.plot(dimensions, norm_skyline)
plt.plot(dimensions, norm_top_k_dominating)
plt.plot(dimensions, norm_top_k_skyline)
plt.legend(["Skyline", "Top-k dominating", "Top-k skyline"])

plt.show()
