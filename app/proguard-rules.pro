# Add project-specific ProGuard rules here as R8-shrinking issues surface in
# release builds (Phase 22 — performance/security pass). Left minimal in
# Phase 1: no reflection-heavy libraries are wired in yet.

# Keep Hilt-generated components' entry points.
-keep class dagger.hilt.internal.aggregatedroot.codegen.* { *; }
-keep class hilt_aggregated_deps.* { *; }
